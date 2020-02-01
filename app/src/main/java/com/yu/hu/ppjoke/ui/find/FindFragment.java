package com.yu.hu.ppjoke.ui.find;



import com.yu.hu.common.fragment.DevelopingFragment;
import com.yu.hu.libnavannoation.FragmentDestination;

@FragmentDestination(pageUrl = "main/tabs/find")
public class FindFragment extends DevelopingFragment {


    @Override
    protected String getText() {
        return "this is find fragment";
    }
}