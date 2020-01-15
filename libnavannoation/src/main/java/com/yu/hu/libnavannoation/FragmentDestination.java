package com.yu.hu.libnavannoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by Hy on 2020/01/14 20:51
 **/
@Target(ElementType.TYPE)
public @interface FragmentDestination {

    String pageUrl();

    boolean needLogin() default false;

    boolean asStarter() default false;
}
