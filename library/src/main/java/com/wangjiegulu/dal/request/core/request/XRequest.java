package com.wangjiegulu.dal.request.core.request;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.wangjiegulu.dal.request.XHttpObservable;
import com.wangjiegulu.dal.request.core.body.XMultiBody;
import com.wangjiegulu.dal.request.core.converter.ResponseConverter;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.TreeMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 11/5/16.
 */
public class XRequest {
    public static final String METHOD_GET = "Get";
    public static final String METHOD_POST = "Post";
    public static final String METHOD_PUT = "Put";
    public static final String METHOD_DELETE = "Delete";
    private static final int TIME_OUT_SECONDS = 30;
    /**
     * 最多重试次数
     */
    private static final int RETRY_MAX_COUNT = 3;

    private static final String TAG = XRequest.class.getSimpleName();

    /**
     * 请求URL
     */
    private String url;

    /**
     * 请求方法
     */
    private String method = METHOD_GET;

    private TreeMap<String, String> headers;

    private int retryCount = RETRY_MAX_COUNT;

    private long timeoutSeconds = TIME_OUT_SECONDS;
    /**
     * 请求参数，指原始的请求参数，比如参数提交前如果需要加密，则这里的参数是指加密之前的参数
     */
    private TreeMap<String, String> parameters;

    /**
     * 文件参数
     */
    private TreeMap<String, XMultiBody> fileParameters;

    /**
     * 真正提交的参数，比如参数提交前如果需要加密，则这里的参数是指加密之后的参数
     */
    private TreeMap<String, String> submitParameters;

    private ResponseConverter responseConverter;

    /**
     * key-value配置，可以在拦截器中通过这里的配置进行相应的处理
     * 比如，每个请求是否需要签名(isSign)等配置信息
     */
    private HashMap<String, Object> requestConfigurations;

    @VisibleForTesting
    public XRequest() {
    }

    private XRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public XRequest get() {
        this.method = METHOD_GET;
        return this;
    }

    public XRequest post() {
        this.method = METHOD_POST;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public XRequest setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    @Nullable
    public TreeMap<String, String> getHeaders() {
        return headers;
    }

    @Nullable
    public TreeMap<String, String> getParameters() {
        return parameters;
    }

    public XRequest addHeader(String name, Object value) {
        if (null == headers) {
            headers = new TreeMap<>();
        }
        headers.put(name, String.valueOf(value));
        return this;
    }

    public XRequest addParameter(String name, @Nullable Object value) {
        if (null != value) {
            if (null == parameters) {
                parameters = new TreeMap<>();
            }
            parameters.put(name, String.valueOf(value));
        }
        return this;
    }

    public XRequest addFileParameter(String name, String filename, String mediaType, File file) {
        if (null == fileParameters) {
            fileParameters = new TreeMap<>();
        }
        fileParameters.put(name, new XMultiBody(filename, RequestBody.create(MediaType.parse(mediaType), file)));
        return this;
    }

    public XRequest addFileParameter(String name, String filename, String mediaType, byte[] fileBytes) {
        if (null == fileParameters) {
            fileParameters = new TreeMap<>();
        }
        fileParameters.put(name, new XMultiBody(filename, RequestBody.create(MediaType.parse(mediaType), fileBytes)));
        return this;
    }

    @Nullable
    public TreeMap<String, XMultiBody> getFileParameters() {
        return fileParameters;
    }

    public long getTimeoutSeconds() {
        return timeoutSeconds;
    }

    public XRequest setTimeoutSeconds(long timeoutSeconds) {
        this.timeoutSeconds = timeoutSeconds;
        return this;
    }

    public TreeMap<String, String> getSubmitParameters() {
        return submitParameters;
    }

    public void setSubmitParameters(TreeMap<String, String> submitParameters) {
        this.submitParameters = submitParameters;
    }

    public boolean hasHeader(String name) {
        return headers.containsKey(name);
    }

    public boolean hasParams(String name) {
        return parameters.containsKey(name);
    }

    public <T> Observable<T> observable(Type responseType) {
        return new XHttpObservable().create(this, responseType);
    }

    public <T> Observable<T> observable(Class<T> responseClass) {
        return new XHttpObservable().create(this, responseClass);
    }

    public static XRequest create(String url) {
        return new XRequest(url);
    }

    /**
     * 增加一个key-value配置
     */
    public XRequest addConfiguration(String key, Object configuration) {
        if (null == requestConfigurations) {
            requestConfigurations = new HashMap<>();
        }
        requestConfigurations.put(key, configuration);
        return this;
    }

    @Nullable
    public <T> T getConfiguration(String key, Class<T> tClass) {
        if (null == requestConfigurations) {
            return null;
        }
        Object value = requestConfigurations.get(key);
        if (null == value) {
            return null;
        }
        Class valueActualClass = value.getClass();
        if (valueActualClass != tClass) {
            Log.e(TAG, "getConfiguration() failed[" + key + "], expect: " + tClass + ", actual: " + valueActualClass);
            return null;
        }
        return tClass.cast(value);
    }

    public boolean isConfiguration(String key, boolean defaultValue) {
        Boolean result = getConfiguration(key, Boolean.class);
        return null == result ? defaultValue : result;
    }

    public ResponseConverter getResponseConverter() {
        return responseConverter;
    }

    public XRequest setResponseConverter(ResponseConverter responseConverter) {
        this.responseConverter = responseConverter;
        return this;
    }

    @Override
    public String toString() {
        return "XRequest{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", headers=" + headers +
                ", parameters=" + parameters +
                ", fileParameters=" + fileParameters +
                ", submitParameters=" + submitParameters +
                ", requestConfigurations=" + requestConfigurations +
                '}';
    }
}
