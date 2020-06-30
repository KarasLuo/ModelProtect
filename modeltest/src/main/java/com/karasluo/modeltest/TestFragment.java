package com.karasluo.modeltest;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.karasluo.libcommon.base.AppFragment;

@Route(path = "/test/testfragment")
public class TestFragment extends AppFragment {
    final static public String TAG="测试fragment";

    private TextView toolbarTitle;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTAG() {
        return TAG;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        toolbarTitle=(TextView) view.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(TAG);
    }

}
