package com.yu.hu.ppjoke.ui.find;



import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.yu.hu.common.fragment.DevelopingFragment;
import com.yu.hu.libnavannoation.FragmentDestination;
import com.yu.hu.ppjoke.model.SofaTab;
import com.yu.hu.ppjoke.ui.sofa.SofaFragment;
import com.yu.hu.ppjoke.utils.AppConfig;

@FragmentDestination(pageUrl = "main/tabs/find")
public class FindFragment extends SofaFragment {

    @Override
    public Fragment getTabFragment(int position) {
        SofaTab.Tabs tab = getTabConfig().tabs.get(position);
        TagListFragment fragment = TagListFragment.newInstance(tab.tag);
        return fragment;
    }

    @Override
    public void onAttachFragment(@NonNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        String tagType = childFragment.getArguments().getString(TagListFragment.KEY_TAG_TYPE);
        if (TextUtils.equals(tagType, "onlyFollow")) {
            ViewModelProviders.of(childFragment).get(TagListViewModel.class)
                    .getSwitchTabLiveData().observe(this,
                    object -> viewPager2.setCurrentItem(1));
        }
    }

    @Override
    public SofaTab getTabConfig() {
        Log.d("aaa", "getTabConfig: "+AppConfig.getFindTabConfig());
        return AppConfig.getFindTabConfig();
    }
}