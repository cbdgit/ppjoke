package com.yu.hu.ppjoke.model;

import java.util.List;

/**
 * 底部菜单栏
 */
@SuppressWarnings("unused")
public class BottomBar {

    /**
     * activeColor : #333333
     * inActiveColor : #666666
     * selectTab : 0
     * tabs : [{"size":24,"enable":true,"index":0,"pageUrl":"main/tabs/home","title":"首页"},{"size":24,"enable":true,"index":1,"pageUrl":"main/tabs/sofa","title":"沙发"},{"size":40,"enable":true,"index":2,"tintColor":"#ff678f","pageUrl":"main/tabs/publish","title":""},{"size":24,"enable":true,"index":3,"pageUrl":"main/tabs/find","title":"发现"},{"size":24,"enable":true,"index":4,"pageUrl":"main/tabs/my","title":"我的"}]
     */

    public String activeColor;
    public String inActiveColor;
    public int selectTab;  //选中的tab index
    public List<Tabs> tabs;

    public static class Tabs {
        /**
         * size : 24
         * enable : true
         * index : 0
         * pageUrl : main/tabs/home
         * title : 首页
         * tintColor : #ff678f
         */

        public int size;
        public boolean enable;
        public int index;
        public String pageUrl;
        public String title;
        public String tintColor;  //用于中间的按钮
    }
}