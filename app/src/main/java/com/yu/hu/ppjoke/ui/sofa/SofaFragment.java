package com.yu.hu.ppjoke.ui.sofa;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.yu.hu.common.fragment.BaseFragment;
import com.yu.hu.libnavannoation.FragmentDestination;
import com.yu.hu.ppjoke.R;
import com.yu.hu.ppjoke.databinding.FragmentSofaBinding;
import com.yu.hu.ppjoke.model.SofaTab;
import com.yu.hu.ppjoke.ui.home.HomeFragment;
import com.yu.hu.ppjoke.utils.AppConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@FragmentDestination(pageUrl = "main/tabs/sofa")
public class SofaFragment extends BaseFragment<FragmentSofaBinding> {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private SofaTab tabConfig;
    private ArrayList<SofaTab.Tabs> tabs;
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();
    private TabLayoutMediator mediator;


    @Override
    protected void onInitView(@Nullable Bundle savedInstanceState) {
        super.onInitView(savedInstanceState);
        viewPager = mDataBinding.viewPager;
        tabLayout = mDataBinding.tabLayout;

        tabConfig = getTabConfig();
        tabs = new ArrayList<>();
        for (SofaTab.Tabs tab : tabConfig.tabs) {
            if (tab.enable) {
                tabs.add(tab);
            }
        }

        //禁止预加载
        viewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        //viewPager2默认只有一种类型的Adapter。FragmentStateAdapter
        //并且在页面切换的时候 不会调用子Fragment的setUserVisibleHint ，取而代之的是onPause(),onResume()、
        viewPager.setAdapter(new FragmentStateAdapter(getChildFragmentManager(), this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
//                Fragment fragment = mFragmentMap.get(position);
//                if (fragment == null) {
//                    fragment = getTabFragment(position);
//                    mFragmentMap.put(position, fragment);
//                }
                //这里不需要自己保管了,FragmentStateAdapter内部自己会管理已实例化的fragment对象。
                return getTabFragment(position);
            }

            @Override
            public int getItemCount() {
                return tabs.size();
            }
        });

        //设置每个tab文本对应的view
        //viewPager2 就不能和再用TabLayout.setUpWithViewPager()了
        //取而代之的是TabLayoutMediator。我们可以在onConfigureTab()方法的回调里面 做tab标签的配置

        //其中autoRefresh的意思是:如果viewPager2 中child的数量发生了变化，也即我们调用了adapter#notifyItemChanged()前后getItemCount不同。
        //要不要 重新刷野tabLayout的tab标签。视情况而定,像咱们sofaFragment的tab数量一旦固定了是不会变的，传true/false  都问题不大
        mediator = new TabLayoutMediator(tabLayout, viewPager, false, (tab, position) -> tab.setCustomView(makeTabView(position)));
        mediator.attach();  //viewpager 与 tabLayout联动

        viewPager.registerOnPageChangeCallback(mPageChangeCallback);

        //切换到默认选择项,那当然要等待初始化完成之后才有效
        viewPager.post(() -> viewPager.setCurrentItem(tabConfig.select, false));
    }

    //页面切换监听以更改tab文本的样式
    ViewPager2.OnPageChangeCallback mPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            int count = tabLayout.getTabCount();
            for (int i = 0; i < count; i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                TextView customView = (TextView) tab.getCustomView();
                if (tab.getPosition() == position) {
                    customView.setTextSize(tabConfig.activeSize);
                    customView.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    customView.setTextSize(tabConfig.normalSize);
                    customView.setTypeface(Typeface.DEFAULT);
                }
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sofa;
    }

    @Override
    public void onDestroy() {
        mediator.detach();
        viewPager.unregisterOnPageChangeCallback(mPageChangeCallback);
        super.onDestroy();

    }

    private SofaTab getTabConfig() {
        return AppConfig.getSofaTabConfig();
    }

    private Fragment getTabFragment(int position) {
        return HomeFragment.newInstance(tabs.get(position).tag);
    }

    private View makeTabView(int position) {
        TextView tabView = new TextView(getContext());
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{Color.parseColor(tabConfig.activeColor), Color.parseColor(tabConfig.normalColor)};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        tabView.setTextColor(colorStateList);
        tabView.setText(tabs.get(position).title);
        tabView.setTextSize(tabConfig.normalSize);
        return tabView;
    }

}