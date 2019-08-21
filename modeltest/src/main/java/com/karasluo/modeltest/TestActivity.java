package com.karasluo.modeltest;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.karasluo.libcommon.base.AppActivity;

@Route(path = "/test/test")
public class TestActivity extends AppActivity {

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    public int getFragmentContainerId() {
        return 0;
    }

    @Override
    public void onFragmentChanged(String title) {

    }
}
