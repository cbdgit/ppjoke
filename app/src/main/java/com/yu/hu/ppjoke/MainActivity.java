package com.yu.hu.ppjoke;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.yu.hu.common.utils.StatusBar;
import com.yu.hu.libnetwork2.ApiResponse;
import com.yu.hu.libnetwork2.GetRequest;
import com.yu.hu.libnetwork2.JsonCallback;
import com.yu.hu.ppjoke.model.Destination;
import com.yu.hu.ppjoke.model.User;
import com.yu.hu.ppjoke.ui.login.UserManager;
import com.yu.hu.ppjoke.ui.view.AppBottomBar;
import com.yu.hu.ppjoke.utils.AppConfig;
import com.yu.hu.ppjoke.utils.NavGraphBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavController navController;
    private AppBottomBar navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        StatusBar.fitSystemBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as PageListPlay set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
        //navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //noinspection ConstantConditions
        navController = NavHostFragment.findNavController(fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);

        //动态构建页面导航
        //NavGraphBuilder.build(navController, this, R.id.nav_host_fragment);
        NavGraphBuilder.build(navController, this, fragment.getId());
        //底部导航点击事件
        navView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        Iterator<Map.Entry<String, Destination>> iterator = destConfig.entrySet().iterator();
        //遍历 target destination 是否需要登录拦截
        while (iterator.hasNext()) {
            Map.Entry<String, Destination> entry = iterator.next();
            Destination value = entry.getValue();
            if (value != null && !UserManager.get().isLogin() && value.isNeedLogin() && value.getId() == menuItem.getItemId()) {
                UserManager.get().login(this).observe(this, new Observer<User>() {
                    @Override
                    public void onChanged(User user) {
                        navView.setSelectedItemId(menuItem.getItemId());
                    }
                });
                return false;
            }
        }

        navController.navigate(menuItem.getItemId());
        //用于区别中间的按钮 返回true不着色
        return !TextUtils.isEmpty(menuItem.getTitle());
    }
}
