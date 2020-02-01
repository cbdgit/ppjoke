package com.yu.hu.ppjoke.ui.my;

import com.yu.hu.common.fragment.DevelopingFragment;
import com.yu.hu.libnavannoation.FragmentDestination;

@FragmentDestination(pageUrl = "main/tabs/my")
public class MyFragment extends DevelopingFragment {

    @Override
    protected String getText() {
        return "this is my fragment";
    }
}