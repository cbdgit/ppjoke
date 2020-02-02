package com.yu.hu.ppjoke.model;

//
@SuppressWarnings("unused")
public class Destination {

    /**
     * isFragment : true
     * asStarter : false
     * needLogin : false
     * pageUrl : main/tabs/notification
     * className : com.yu.hu.ppjoke.ui.notifications.NotificationsFragment
     * id : 2046474421
     */

    private boolean isFragment;
    private boolean asStarter;
    private boolean needLogin;
    private String pageUrl;
    private String className;
    private int id;  //Math.abs(className.hashCode())

    public boolean isFragment() {
        return isFragment;
    }

    public boolean isStarter() {
        return asStarter;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public String getClassName() {
        return className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
