package com.yu.hu.ppjoke.ui.sofa;



import com.yu.hu.common.fragment.DevelopingFragment;
import com.yu.hu.libnavannoation.FragmentDestination;

@FragmentDestination(pageUrl = "main/tabs/sofa")
public class SofaFragment extends DevelopingFragment {

    @Override
    protected String getText() {
        return "this is sofa fragment";
    }
}