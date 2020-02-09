package com.yu.hu.ppjoke.navigator;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

import com.yu.hu.common.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Map;

/**
 * @author Hy
 * created on 2020/02/09 10:34
 * <p>
 * 自定义FragmentNavigator以将fragment切换方式改为hide/show，
 * 原生实现方式是通过replace方式实现的，每次切换时都会导致其生命周期重启
 **/
@Navigator.Name("fixfragment")
public class FixFragmentNavigator extends FragmentNavigator {

    private static final String TAG = "FixFragmentNavigator";
    private Context mContext;
    private FragmentManager mFragmentManager;
    private int mContainerId;

    public FixFragmentNavigator(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        super(context, manager, containerId);
        //父类中为私有变量，所以这里再定义一个
        mContext = context;
        mFragmentManager = manager;
        mContainerId = containerId;
    }

    //重写navigate  将fragment切换方式改为hide/show
    @SuppressWarnings("ConstantConditions")
    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        if (mFragmentManager.isStateSaved()) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already"
                    + " saved its state");
            return null;
        }

        String className = destination.getClassName();
        if (className.charAt(0) == '.') {
            className = mContext.getPackageName() + className;
        }
        //final Fragment frag = instantiateFragment(mContext, mFragmentManager,
        //className, args);
        //frag.setArguments(args);
        final FragmentTransaction ft = mFragmentManager.beginTransaction();

        int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
        int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
        int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
        int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = enterAnim != -1 ? enterAnim : 0;
            exitAnim = exitAnim != -1 ? exitAnim : 0;
            popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
            popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        }

        //获取当前所显示的fragment并隐藏
        Fragment fragment = mFragmentManager.getPrimaryNavigationFragment();
        if (fragment != null) {
            ft.hide(fragment);
        }

        //显示/创建需要显示的fragment
        Fragment frag;
        String tag = String.valueOf(destination.getId());
        frag = mFragmentManager.findFragmentByTag(tag);
        if (frag != null) {
            ft.show(frag);
        } else {
            frag = instantiateFragment(mContext, mFragmentManager, className, args);
            frag.setArguments(args);
            ft.add(mContainerId, frag, tag);
        }

        //ft.replace(mContainerId, frag);
        ft.setPrimaryNavigationFragment(frag);

        final @IdRes int destId = destination.getId();

        //通过反射拿取mBackStack
        //是一个数组 其中管理的是fragment的回退堆栈
        ArrayDeque<Integer> mBackStack = null;
        boolean mIsPendingBackStackOperation = false;
        try {
            Field field = FragmentNavigator.class.getDeclaredField("mBackStack");
            field.setAccessible(true);
            //noinspection unchecked
            mBackStack = (ArrayDeque<Integer>) field.get(this);


            field = FragmentNavigator.class.getDeclaredField("mIsPendingBackStackOperation");
            field.setAccessible(true);
            mIsPendingBackStackOperation = (boolean) field.get(this);
        } catch (Exception e) {
            LogUtil.warn(e);
        }

        final boolean initialNavigation = mBackStack.isEmpty();
        // TODO Build first class singleTop behavior for fragments
        final boolean isSingleTopReplacement = navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId;

        boolean isAdded;
        if (initialNavigation) {
            isAdded = true;
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size() > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mFragmentManager.popBackStack(
                        generateBackStackName(mBackStack.size(), mBackStack.peekLast()),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.addToBackStack(generateBackStackName(mBackStack.size(), destId));
                mIsPendingBackStackOperation = true;
            }
            isAdded = false;
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size() + 1, destId));
            mIsPendingBackStackOperation = true;
            isAdded = true;
        }

        //再将mIsPendingBackStackOperation设置回去
        try {
            Field field = FragmentNavigator.class.getDeclaredField("mIsPendingBackStackOperation");
            field.setAccessible(true);
            field.set(this, mIsPendingBackStackOperation);
            field.setAccessible(false);
        } catch (Exception e) {
            LogUtil.warn(e);
        }

        if (navigatorExtras instanceof Extras) {
            Extras extras = (Extras) navigatorExtras;
            for (Map.Entry<View, String> sharedElement : extras.getSharedElements().entrySet()) {
                ft.addSharedElement(sharedElement.getKey(), sharedElement.getValue());
            }
        }
        ft.setReorderingAllowed(true);
        ft.commit();
        // The commit succeeded, update our view of the world
        if (isAdded) {
            mBackStack.add(destId);
            return destination;
        } else {
            return null;
        }
    }

    //同父类方法一样
    @NonNull
    private String generateBackStackName(int backStackIndex, int destId) {
        return backStackIndex + "-" + destId;
    }
}
