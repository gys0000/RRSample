package com.example.gystr.net;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.ImageView;

import com.example.gystr.R;
import com.example.rr.GlideHelper;

public class Utils {
    /**
     * 自定义属性
     * 扩展属性
     * 添加这个属性之后，放到ImageView布局文件中，相当于加载图片到ImageView控件中
     *
     * @param view       一定是
     * @param imgUrl
     */
    @BindingAdapter({"imgUrl"})
    public static void loadImg(ImageView view, String imgUrl) {
        if (imgUrl == null) {
            view.setImageResource(R.mipmap.ic_launcher);
        } else {
            GlideHelper.urlToImageView(view.getContext(), view, imgUrl, R.mipmap.ic_launcher);
        }
    }

    @BindingAdapter({"imgUrl", "placeHolder"})
    public static void loadImgUrl(ImageView view, String imgUrl, Drawable drawable) {
        if (imgUrl == null) {
            view.setImageDrawable(drawable);
        } else {
            GlideHelper.urlToCircleImageView(view.getContext(), view, imgUrl, drawable);
        }
    }

    @BindingAdapter({"linear"})
    public static void initRecycleLinear(RecyclerView recyclerView, int direction) {
        LinearLayoutManager linearLayoutManager;
        if (direction == 1) {
            linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        } else {
            linearLayoutManager = new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL, false);
        }
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @BindingAdapter({"grid"})
    public static void initRecycleGrid(RecyclerView recyclerView, int num) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), num);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @BindingAdapter({"staggeredGrid"})
    public static void initRecycleStaggeredGrid(RecyclerView recyclerView, int num) {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(num, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

}
