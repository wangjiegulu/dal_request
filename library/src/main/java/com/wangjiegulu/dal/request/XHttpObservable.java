package com.wangjiegulu.dal.request;

import android.net.Uri;
import android.util.Log;

import com.wangjiegulu.dal.request.core.XHttpClient;
import com.wangjiegulu.dal.request.core.body.XMultiBody;
import com.wangjiegulu.dal.request.core.converter.ResponseConverter;
import com.wangjiegulu.dal.request.core.interceptor.IOriginResponseInterceptor;
import com.wangjiegulu.dal.request.core.interceptor.IRequestInterceptor;
import com.wangjiegulu.dal.request.core.interceptor.IResponseInterceptor;
import com.wangjiegulu.dal.request.core.request.XRequest;
import com.wangjiegulu.dal.request.core.request.XRequestBuilder;
import com.wangjiegulu.dal.request.util.ExceptionUtil;
import com.wangjiegulu.dal.request.util.XIOUtil;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiPredicate;
import io.reactivex.functions.Function;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.GzipSource;
import okio.Okio;

/**
 * wangjie
 */
public class XHttpObservable {
    private static final String TAG = XHttpObservable.class.getSimpleName();


    private static void addParametersForGet(XRequestBuilder builder, XRequest xRequest) {
        TreeMap<String, String> submitParameters = xRequest.getSubmitParameters();
        if (null == submitParameters || submitParameters.isEmpty()) {
            return;
        }
        Uri.Builder uriBuilder = Uri.parse(xRequest.getUrl()).buildUpon();
        for (Map.Entry<String, String> entry : submitParameters.entrySet()) {
            uriBuilder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        builder.url(uriBuilder.build().toString());
    }

    private static void addParametersForPost(XRequestBuilder builder, XRequest xRequest) {
        TreeMap<String, String> submitParameters = xRequest.getSubmitParameters();
        if (null == submitParameters || submitParameters.isEmpty()) {
            return;
        }
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : submitParameters.entrySet()) {
            bodyBuilder.add(entry.getKey(), entry.getValue());
        }
        builder.post(bodyBuilder.build());
    }

    private static void addParametersForFiles(XRequestBuilder builder, XRequest xRequest) {
        TreeMap<String, String> submitParameters = xRequest.getSubmitParameters();
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (null != submitParameters && !submitParameters.isEmpty()) {
            for (Map.Entry<String, String> entry : submitParameters.entrySet()) {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }

        TreeMap<String, XMultiBody> fileParameters = xRequest.getFileParameters();
        if (null != fileParameters && !fileParameters.isEmpty()) {
            for (Map.Entry<String, XMultiBody> entry : fileParameters.entrySet()) {
                XMultiBody xMultiBody = entry.getValue();
                bodyBuilder.addFormDataPart(entry.getKey(), xMultiBody.getFilename(), xMultiBody.getRequestBody());
            }
        }
        builder.post(bodyBuilder.build());
    }


    public <T> Observable<T> create(final XRequest xRequest, final Type httpResponseType) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                ResponseBody responseBody = null;
                try {
                    XRequestBuilder builder = XRequestBuilder.create();
                    builder.url(xRequest.getUrl());

                    // 请求拦截器
                    List<IRequestInterceptor> requestInterceptorList = XHttpManager.getInstance().getRequestInterceptorList();
                    for (IRequestInterceptor interceptor : requestInterceptorList) {
                        interceptor.onRequestIntercept(xRequest);
                    }

                    // 添加Header
                    TreeMap<String, String> headers = xRequest.getHeaders();
                    if (null != headers && !headers.isEmpty()) {
                        for (Map.Entry<String, String> entry : headers.entrySet()) {
                            builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }

                    // 添加所有参数
                    TreeMap<String, XMultiBody> fileParameters = xRequest.getFileParameters();
                    if (null != fileParameters && !fileParameters.isEmpty()) { // 有文件上传，则默认post
                        addParametersForFiles(builder, xRequest);
                    } else {
                        switch (xRequest.getMethod()) {
                            case XRequest.METHOD_GET:
                                addParametersForGet(builder, xRequest);
                                break;
                            case XRequest.METHOD_POST:
                            default:
                                addParametersForPost(builder, xRequest);
                                break;
                        }
                    }
                    Response response = XHttpClient.get().newCall(builder.build()).execute();
                    T t;
                    responseBody = response.body();

                    byte[] responseBytes;
                    if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))) {
                        // Okio.buffer(new GzipSource(response.body().source())).readUtf8()
//                        reader = new InputStreamReader(Okio.buffer(new GzipSource(responseBody.source())).inputStream());
                        responseBytes = Okio.buffer(new GzipSource(responseBody.source())).readByteArray();
                    } else {
                        // response.body().string()
//                        reader = responseBody.charStream();
                        responseBytes = responseBody.bytes();
                    }

                    // 原始响应拦截器;
                    IOriginResponseInterceptor originResponseInterceptor = XHttpManager.getInstance().getOriginResponseInterceptor();
                    if (null != originResponseInterceptor) {
                        responseBytes = originResponseInterceptor.onOriginResponseIntercept(xRequest, responseBytes);
                    }

                    ResponseConverter converter = xRequest.getResponseConverter();
                    if (null == converter) {
                        converter = XHttpManager.getInstance().getResponseConverter();
                    }
                    if (null == converter) {
                        throw new RuntimeException("No available ResponseConverter!");
                    }
                    t = converter.convert(xRequest, responseBytes, httpResponseType);
//                    t = gson.fromJson(reader = new InputStreamReader(new ByteArrayInputStream(responseBytes)), httpResponseClass);
//                    t = gson.fromJson(reader, httpResponseClass);
                    if (XHttpManager.getInstance().isDebug()) {
                        Log.d(TAG, "xRequest-url: " + xRequest.getUrl());
                    }
                    if (null != t && XHttpManager.getInstance().isDebug()) {
                        Log.d(TAG, "response: " + t.toString());
                    }

                    if (!emitter.isDisposed()) {
                        emitter.onNext(t);
                        emitter.onComplete();
                    }
                } catch (Throwable throwable) {
                    if (XHttpManager.getInstance().isDebug()) {
                        Log.e(TAG, "xRequest-url: " + xRequest.getUrl());
                    }
                    Log.e(TAG, "", throwable);
                    if (!emitter.isDisposed()) {
                        emitter.onError(throwable);
                    }
                } finally {
                    XIOUtil.closeIO(responseBody);
                }
            }
        })
                .timeout(xRequest.getTimeoutSeconds(), TimeUnit.SECONDS)
                .retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(Integer integer, Throwable throwable) throws Exception {
                        if (XHttpManager.getInstance().isDebug()) {
                            Log.w(TAG, "throwable: " + throwable);
                        }
                        boolean retry = integer <= xRequest.getRetryCount() && ExceptionUtil.isNetworkError(throwable);
                        if (retry && XHttpManager.getInstance().isDebug()) {
                            Log.w(TAG, "retry: " + integer + ", request: " + xRequest);
                        }
                        return retry;
                    }
                })
                .map(new Function<T, T>() {
                    @Override
                    public T apply(T t) throws Exception {
                        // Response拦截器
                        try {
                            for (IResponseInterceptor responseInterceptor : XHttpManager.getInstance().getResponseInterceptorList()) {
                                responseInterceptor.onResponseIntercept(xRequest, t);
                            }
                        } catch (Throwable throwable) {
                            new RuntimeException(throwable);
                        }

                        return t;
                    }
                });

    }

}



