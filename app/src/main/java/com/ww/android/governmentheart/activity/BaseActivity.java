package com.ww.android.governmentheart.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.ww.android.governmentheart.BaseApplication;
import com.ww.android.governmentheart.R;
import com.ww.android.governmentheart.config.type.ImmersionType;
import com.ww.android.governmentheart.mvp.model.IModel;
import com.ww.android.governmentheart.mvp.presenter.PresenterActivity;
import com.ww.android.governmentheart.mvp.vu.IView;
import com.ww.android.governmentheart.utils.ToastUtils;
import com.ww.android.governmentheart.widget.dialog.LoadingDialog;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * @Author feng
 * @Date 2018/6/10
 */
public abstract class BaseActivity<V extends IView, M extends IModel> extends
        PresenterActivity<V, M> {
    protected BaseApplication baseApp;

    @Nullable
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @Nullable
    @BindView(R.id.btn_title_right)
    public Button btnTitleRight;
    @Nullable
    @BindView(R.id.btn_title_left)
    public Button btnTitleLeft;

    protected ImmersionBar mImmersionBar;
    protected LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseApp = BaseApplication.getInstance();
        if (isImmersionBarEnabled()) {
            if (isDefaultImmersionBar()) {
                initDefaultImmersionBar(initDefaultImmersionType());
            } else {
                initImmersionBar();
            }
        }
        init();
    }

    public void showToast(CharSequence text) {
        ToastUtils.showToast(text);
    }

    @Optional
    @OnClick({R.id.btn_title_left, R.id.btn_title_right})
    public void onTitleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_title_left:
                onTitleLeft();
                break;
            case R.id.btn_title_right:
                onTitleRight();
                break;
            default:
                break;
        }
    }

    public void onTitleLeft() {
        finish();
    }

    public void onTitleRight() {

    }

    @Optional
    public void setTitleText(String titleText) {
        if (tvTitle != null) {
            tvTitle.setText(titleText);
        }
    }

    public void setTitleLeftText(String titleLeftText) {
        if (btnTitleLeft != null) {
            btnTitleLeft.setText(titleLeftText);
        }
    }

    public void setTitleRightText(String titleRightText) {
        if (btnTitleRight != null) {
            btnTitleRight.setText(titleRightText);
        }
    }


    protected void showLoading(){
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }

    protected void cancelLoading(){
        if (mLoadingDialog!=null){
            mLoadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    protected void initDefaultImmersionBar(int type) {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        if (ImmersionType.RED == type) {
            mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary).init();
        } else if (ImmersionType.WHITE == type) {
            mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_white)
                    .statusBarDarkFont
                    (true, 0.2f).init();
        }else if (ImmersionType.BLUE == type){
            mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.color_blue).init();
        }
    }


    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 是否使用默认沉浸式
     *
     * @return
     */
    protected boolean isDefaultImmersionBar() {
        return true;
    }

    protected int initDefaultImmersionType() {
        return ImmersionType.RED;
    }
}
