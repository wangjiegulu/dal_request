package com.wangjie.dal.request.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wangjiegulu.dal.request.XHttpManager;
import com.wangjiegulu.dal.request.core.request.XRequestCreator;
import com.wangjiegulu.dal.request.gson.DefaultGsonResponseConverter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XHttpManager.getInstance()
                .setResponseConverter(DefaultGsonResponseConverter.create())
                .setDebug(true);

        new XRequestCreator() // Inject XRequestCreator via Dagger2.
                .createRequest("http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1")
                .addParameter("param1", 1)
                .addParameter("param2", "content")
                .addConfiguration("isSign", false)
                .addConfiguration("otherKey", "asdfasdfdsaadf2342353")
                .setRetryCount(2)
                .observable(StringResponse.class)
//                .<StringResponse>observable(TypeToken.get(StringResponse.class).getType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StringResponse>() {
                    @Override
                    public void accept(StringResponse dalBaseResponse) throws Exception {
                        Log.i(TAG, "dalBaseResponse: " + dalBaseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "", throwable);
                    }
                });

    }
}
