package com.yu.hu.ppjoke.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.yu.hu.common.utils.ConvertUtil;
import com.yu.hu.ppjoke.R;
import com.yu.hu.ppjoke.model.BottomBar;
import com.yu.hu.ppjoke.model.Destination;
import com.yu.hu.ppjoke.utils.AppConfig;

import java.util.List;

/**
 * 自定义BottomView
 */
public class AppBottomBar extends BottomNavigationView {

    private static int[] sIcons = new int[]{R.drawable.icon_tab_home, R.drawable.icon_tab_sofa,
            R.drawable.icon_tab_publish, R.drawable.icon_tab_find, R.drawable.icon_tab_mine};

    public AppBottomBar(Context context) {
        this(context, null);
    }

    public AppBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        BottomBar bottomBar = AppConfig.getBottomBar();
        List<BottomBar.Tabs> tabs = bottomBar.tabs;

        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};

        int[] colors = new int[]{Color.parseColor(bottomBar.activeColor), Color.parseColor(bottomBar.inActiveColor)};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        //icon颜色
        setItemIconTintList(colorStateList);
        //文字颜色
        setItemTextColor(colorStateList);
        //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        setSelectedItemId(bottomBar.selectTab);

        for (BottomBar.Tabs tab : tabs) {
            if (!tab.enable) {
                continue;
            }
            int itemId = getId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }
            MenuItem menuItem = getMenu().add(0, itemId, tab.index, tab.title);
            menuItem.setIcon(sIcons[tab.index]);

        }

        //设置icon大小
        //每次add添加 之后都会重新绘制 所以要全部添加完成后再设置大小
        int index = 0;
        for (BottomBar.Tabs tab : tabs) {

            if (!tab.enable) {
                continue;
            }
            int itemId = getId(tab.pageUrl);
            if (itemId < 0) {
                continue;
            }

            int iconSize = ConvertUtil.dp2px(getContext(), tab.size);
            //BottomNavigationView的第一个子view
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            //获取到每一个
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(index);
            itemView.setIconSize(iconSize);

            if (TextUtils.isEmpty(tab.title)) {
                itemView.setIconTintList(ColorStateList.valueOf(Color.parseColor(tab.tintColor)));
                ////禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);
            }
            index++;
        }

    }

    private int getId(String pageUrl) {
        Destination destination = AppConfig.getDestConfig().get(pageUrl);
        if (destination == null) {
            return -1;
        }
        return destination.getId();
    }
}
