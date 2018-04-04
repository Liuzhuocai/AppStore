package com.elf.appstore.widget.ui.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elf.appstore.R;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mtoolBar;
    private LinearLayout mDectorView = null;//根布局
    private FrameLayout mContentView = null;//activity内容布局
    private TextView toolBarCenterTitleTv, toolBarRightTitleTv, toolBarLeftTitleTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mDectorView == null) {
            initDectorView();
        }
        setSupportActionBar(mtoolBar);
        //如果已经创建就先把内容清空，再添加
        if (mContentView != null) {
            mContentView.removeAllViews();
        }
    }

    /**
     * 重写setContentView方法，使mContentView成为子activity界面的父布局
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getLayoutInflater().inflate(layoutResID, mContentView);
        super.setContentView(mDectorView);
    }

    /**
     * 初始化toolbar
     */
    private void initToolBar() {
        View view = getLayoutInflater().inflate(R.layout.activity_base, mDectorView);
        mtoolBar = (Toolbar) view.findViewById(R.id.app_toolbar);
        toolBarCenterTitleTv = (TextView) view.findViewById(R.id.tool_bar_center_title_tv);
        toolBarRightTitleTv = (TextView) view.findViewById(R.id.tool_bar_right_title_tv);
        toolBarLeftTitleTv = (TextView) view.findViewById(R.id.tool_bar_left_title_tv);
        toolBarRightTitleTv.setOnClickListener(this);
        toolBarLeftTitleTv.setOnClickListener(this);
        mtoolBar.setTitle("");
        setCustomToolbar(mtoolBar);
    }

    /**
     * 让子activity自己定义toolbar
     *
     * @param mtoolBar
     */
    protected void setCustomToolbar(Toolbar mtoolBar) {

    }

    public void showCenterTitle(String title) {
        toolBarCenterTitleTv.setVisibility(View.VISIBLE);
        toolBarCenterTitleTv.setText(title);
    }

    public void showRightTitle(String title) {
        toolBarRightTitleTv.setVisibility(View.VISIBLE);
        toolBarRightTitleTv.setText(title);
    }


    /**
     * 初始化根布局
     */
    private void initDectorView() {
        mDectorView = new LinearLayout(this);
        mDectorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDectorView.setOrientation(LinearLayout.VERTICAL);
        initToolBar();
        mContentView = new FrameLayout(this);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDectorView.addView(mContentView);
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
//                ToastMaker.showToastShort("搜索");
                AppUtils.jumpActivity(this, SeachActivity.class);
                break;
            case R.id.action_download:
//                ToastMaker.showToastShort("下载");
                AppUtils.jumpActivity(this, F_download.class);
                break;
            case R.id.action_share:
                AppUtils.showShare(this);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }*/

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tool_bar_right_title_tv) {
            onRightTitleListeren();
        } else if (view == toolBarLeftTitleTv) {
            onBackPressed();
        }
    }

    protected void onRightTitleListeren() {

    }

    public void setCenterTitle(int resId) {
        if (toolBarCenterTitleTv != null) {
            toolBarCenterTitleTv.setText(resId);
        }

    }
    public void setBackTitleDrawable(Drawable drawable) {
        if (toolBarLeftTitleTv != null) {
            toolBarLeftTitleTv.setCompoundDrawables(drawable,null,null,null);
        }
    }
    public void setLeftTitle(int resId) {
        if (toolBarRightTitleTv != null) {
            toolBarRightTitleTv.setVisibility(View.VISIBLE);
            toolBarRightTitleTv.setText(resId);
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
