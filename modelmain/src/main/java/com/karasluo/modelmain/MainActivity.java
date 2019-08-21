package com.karasluo.modelmain;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.karasluo.libcommon.base.AppActivity;
import com.karasluo.libcommon.base.AppFragment;
import com.karasluo.libcommon.widget.TipDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppActivity {
    static final private String TAG="MainActivity";

    private TextView toolbarTitle;
    @Override
    public void initData() {
        requestPermission();
    }

    @Override
    public void initView() {
        toolbarTitle=(TextView) findViewById(R.id.toolbar_title);
        AppFragment fragment=(AppFragment)ARouter.getInstance()
                .build("/test/testfragment")
                .navigation();
        addFragment(fragment);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    public void onFragmentChanged(String title) {
        toolbarTitle.setText(title);
    }

    /**
     * 获取动态权限
     */
    public void requestPermission(){
        RxPermissions rxPermissions=new RxPermissions(this);
        Disposable disposable = rxPermissions.request(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
//                Manifest.permission.INTERNET,
//                Manifest.permission.READ_LOGS
        )
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //已开启所有权限
                            Log.i(TAG, "已开启所有权限");
                        } else {
                            //权限被拒绝
                            Log.e(TAG, "权限被拒绝");
                            TipDialog tipDialog = new TipDialog(MainActivity.this,
                                    "权限受限，功能将无法使用，\n请前往手机设置开启权限！");
                            tipDialog.show();
                        }
                    }
                });
    }

    @Override
    public boolean swipeBackPriority() {
        return false;//activity不通过滑动退出
    }

    //记录用户首次点击返回键的时间
    private long firstTime = 0;
    @Override
    public void onBackPressed() {
        if(removeFragmentWithAnimations()){
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
//                showToast("再点一次退出程序！");
                firstTime = secondTime;
            } else{
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
        }
    }
}
