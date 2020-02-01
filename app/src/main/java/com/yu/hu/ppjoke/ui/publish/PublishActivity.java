package com.yu.hu.ppjoke.ui.publish;

import com.yu.hu.common.activity.BaseActivity;
import com.yu.hu.libnavannoation.ActivityDestination;
import com.yu.hu.ppjoke.R;
import com.yu.hu.ppjoke.databinding.ActivityPublishBinding;

@ActivityDestination(pageUrl = "main/tabs/publish", needLogin = true)
public class PublishActivity extends BaseActivity<ActivityPublishBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }
}
