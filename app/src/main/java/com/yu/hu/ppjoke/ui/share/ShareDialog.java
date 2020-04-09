package com.yu.hu.ppjoke.ui.share;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yu.hu.ppjoke.R;
import com.yu.hu.ppjoke.ui.RoundFrameLayout;
import com.yu.hu.ppjoke.ui.ViewHelper;
import com.yu.hu.ppjoke.utils.PixUtils;
import com.yu.hu.ppjoke.view.PPImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hy
 * created on 2020/04/09 10:28
 **/
public class ShareDialog extends AlertDialog {

    private ShareAdapter shareAdapter;

    private String shareContent;
    List<ResolveInfo> shareItems = new ArrayList<>();

    private View.OnClickListener mListener;

    public ShareDialog(@NonNull Context context) {
        super(context);
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public void setShareItemClickListener(View.OnClickListener listener) {

        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        RoundFrameLayout layout = new RoundFrameLayout(getContext());
        layout.setBackgroundColor(Color.WHITE);
        layout.setViewOutLine(PixUtils.dp2px(20), ViewHelper.RADIUS_TOP);

        RecyclerView gridView = new RecyclerView(getContext());
        gridView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        shareAdapter = new ShareAdapter();
        gridView.setAdapter(shareAdapter);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int margin = PixUtils.dp2px(20);

        params.leftMargin = params.topMargin = params.rightMargin = params.bottomMargin = margin;
        params.gravity = Gravity.CENTER;
        layout.addView(gridView, params);

        setContentView(layout);
        getWindow().setGravity(Gravity.BOTTOM);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        queryShareItems();
    }

    //查询分享入口--是否安装有QQ，微信等
    private void queryShareItems() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        List<ResolveInfo> resolveInfos = getContext().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {

            String packageName = resolveInfo.activityInfo.packageName;
            //过滤出qq 或 微信
            if (TextUtils.equals(packageName, "com.tencent.mm") || TextUtils.equals(packageName, "com.tencent.mobileqq")) {
                shareItems.add(resolveInfo);
            }
        }
        shareAdapter.notifyDataSetChanged();
    }

    private class ShareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final PackageManager packageManager;

        public ShareAdapter() {
            packageManager = getContext().getPackageManager();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_share_item, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ResolveInfo resolveInfo = shareItems.get(position);
            PPImageView imageView = holder.itemView.findViewById(R.id.share_icon);
            Drawable drawable = resolveInfo.loadIcon(packageManager);
            imageView.setImageDrawable(drawable);

            TextView shareText = holder.itemView.findViewById(R.id.share_text);
            shareText.setText(resolveInfo.loadLabel(packageManager));

            //item点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pkg = resolveInfo.activityInfo.packageName;
                    String cls = resolveInfo.activityInfo.name;
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.setComponent(new ComponentName(pkg, cls));
                    intent.putExtra(Intent.EXTRA_TEXT, shareContent);

                    getContext().startActivity(intent);

                    if (mListener != null) {
                        mListener.onClick(v);
                    }

                    dismiss();
                }
            });
        }

        @Override
        public int getItemCount() {
            return shareItems == null ? 0 : shareItems.size();
        }
    }
}
