package com.yu.hu.common.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.yu.hu.common.R;
import com.yu.hu.common.databinding.FragmentDevelopBinding;

/**
 * 开发中的fragment
 *
 * @see #getText() 重写此方法以设置所展示的文字
 */
public abstract class DevelopingFragment extends BaseFragment<FragmentDevelopBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_develop;
    }

    @Override
    protected void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        setText(getText());
    }

    protected String getText() {
        return "developing";
    }

    @SuppressWarnings("WeakerAccess")
    public void setText(String text) {
        mDataBinding.tvTitle.setText(text);
    }

}
