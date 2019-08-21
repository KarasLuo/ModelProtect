package com.karasluo.libcommon.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * Created by Hongliang Luo on 2019/8/16.
 **/
public class BaseApplication extends Application {
    final static private String TAG="BaseApplication";
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        //do something
    }

    public static Context getAppContext(){
        return context;
    }

    @Override
    public Resources getResources() {
        Resources res=super.getResources();
        Configuration configuration=res.getConfiguration();
        if(configuration.fontScale!=1.0f){
            configuration.fontScale=1.0f;//app的字体缩放还原为1.0，即不缩放
            res.updateConfiguration(configuration,res.getDisplayMetrics());
        }
        return res;
    }
}
