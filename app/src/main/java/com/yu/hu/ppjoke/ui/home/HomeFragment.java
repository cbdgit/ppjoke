package com.yu.hu.ppjoke.ui.home;



import com.yu.hu.common.fragment.DevelopingFragment;
import com.yu.hu.libnavannoation.FragmentDestination;

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
public class HomeFragment extends DevelopingFragment {

    @Override
    protected String getText() {
        return "this is home fragment";
    }
}