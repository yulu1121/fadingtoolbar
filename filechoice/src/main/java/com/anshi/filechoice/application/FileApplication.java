package com.anshi.filechoice.application;

import android.app.Application;
import android.util.Log;

import com.anshi.filechoice.utils.ExceptionHandler;
import com.tencent.smtt.sdk.QbSdk;

/**
 *
 * Created by yulu on 2017/11/29.
 */

public class FileApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.e("xxx","fail");
                // TODO Auto-generated method stub
            }
        };
        QbSdk.initX5Environment(this,cb);
       // ExceptionHandler.getInstance().initConfig(this);
    }
}
