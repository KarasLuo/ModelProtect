package com.karasluo;

import com.alibaba.android.arouter.launcher.ARouter;
import com.karasluo.libcommon.base.BaseApplication;
import com.karasluo.libcommon.utils.CrashHandler;

/**
 * Created by Hongliang Luo on 2019/8/16.
 **/
public class AppApplication  extends BaseApplication {
    private static final String TAG="AppApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        //崩溃本地记录初始化
//        CrashHandler.getInstance().init(getAppContext());
        //路由框架初始化
        if(BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
            ARouter.printStackTrace();
        }
        ARouter.init(this);
        //其它初始化
    }

}
