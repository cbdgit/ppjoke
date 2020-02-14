package com.yu.hu.ppjoke;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;

import com.alibaba.fastjson.JSONObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.JsonObject;
import com.yu.hu.libnetwork2.ApiResponse;
import com.yu.hu.libnetwork2.GetRequest;
import com.yu.hu.libnetwork2.JsonCallback;
import com.yu.hu.ppjoke.utils.NavGraphBuilder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
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


        GetRequest<JSONObject> request = new GetRequest<>("www.imooc.com");
        request.execute();

        request.execute(new JsonCallback<JSONObject>() {
            @Override
            public void onSuccess(ApiResponse<JSONObject> response) {
                super.onSuccess(response);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        navController.navigate(menuItem.getItemId());
        //用于区别中间的按钮 返回true不着色
        return !TextUtils.isEmpty(menuItem.getTitle());
    }
}
