package com.yu.hu.ppjoke.detail;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import com.yu.hu.ppjoke.R;

/**
 * @author Hy
 * created on 2020/04/11 14:02
 **/
public class VideoViewHandler extends ViewHandler {

    //private LayoutFeedDetailTypeVideoBinding mVideoBinding;
    private String category;
    private boolean backPressed;

    public VideoViewHandler(FragmentActivity activity) {
        super(activity);

        //mVideoBinding = DataBindingUtil.setContentView(activity, R.layout.layout_feed_detail_type_video);
    }
}
