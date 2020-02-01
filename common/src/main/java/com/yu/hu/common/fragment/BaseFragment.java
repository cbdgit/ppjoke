package com.yu.hu.common.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.yu.hu.common.R;
import com.yu.hu.common.dialog.LoadingDialog;

/**
 * Created by Hy on 2019/11/28 17:04
 * fragment基类
 * <p>
 * 方法：
 *
 * @see #getLayoutId() 必须重写
 * @see #onInitView(Bundle)
 * @see #onInitEvents(View, Bundle)
 * @see #getLoadingDialog() 可以重写 返回已自定义loadingDialog样式
 * @see #showLoadingDialog()  or {@link #showLoadingDialog(String)}
 * <p>
 * 属性：
 * @see #mContext  == this
 * @see #mDataBinding mDataBinding
 * @see #mLoadingDialog LoadingDialog
 * @see #mLayoutInflater mLayoutInflater
 **/
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class BaseFragment<D extends ViewDataBinding> extends Fragment {

    protected Context mContext;
    protected D mDataBinding;
    protected View mRootView;
    protected LoadingDialog mLoadingDialog;
    protected LayoutInflater mLayoutInflater;

    @CallSuper
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mDataBinding.getRoot();
        return mRootView;
    }

    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInitView(savedInstanceState);
        onInitEvents(mRootView, savedInstanceState);
    }

    /**
     * view的一些初始化，不要有太耗时的操作否则会影响显示
     *
     * @see #onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    protected void onInitView(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 初始化事件
     *
     * @see #onViewCreated(View, Bundle)
     */
    protected void onInitEvents(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * showLoadingDialog
     *
     * @see #mLoadingDialog
     */
    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    /**
     * showLoadingDialog
     * 指定content，注意指定过后之后显示的content都是设置的content
     *
     * @param content content
     */
    public void showLoadingDialog(String content) {
        if (mLoadingDialog == null) {
            mLoadingDialog = getLoadingDialog();
        }
        if (!TextUtils.isEmpty(content)) {
            mLoadingDialog.setContent(content);
        }
        mLoadingDialog.show(getChildFragmentManager());
    }

    /**
     * hideLoadingDialog
     */
    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isVisible()) {
            mLoadingDialog.dismiss();
        }
    }

    /**
     * 可以自定义dialog样式
     *
     * @return LoadingDialog
     * @see LoadingDialog
     */
    protected LoadingDialog getLoadingDialog() {
        return LoadingDialog.newInstance()
                .setContent(R.string.loading)
                .setContentColorResource(R.color.colorPrimary)
                .setProgressBarColorResource(R.color.colorPrimary);
    }
}
