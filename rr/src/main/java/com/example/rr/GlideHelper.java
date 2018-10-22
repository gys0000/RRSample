package com.example.rr;/**
 * Created by Administrator on 2017/4/10.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 作者:LiuYF on 2017/4/10 17:31
 * Glide帮助类
 */

public class GlideHelper {

    /**
     * Fill an ImageView with a picture from the resources using Glide.
     * 填写使用资源图片到ImageView
     *
     * @param context       the Context for where to load
     * @param imageView     the ImageView to fill with an image
     * @param resDrawableId the resource drawable id
     */
    public static void resDrawableToImageView(Context context, ImageView imageView, int resDrawableId) {
        if (context == null || imageView == null) {
            return;
        }
        Glide.with(context)
                .load(resDrawableId)
                .thumbnail(0.1f)
                .into(imageView);
    }

    public static void disPlayRound(Context context, ImageView imageView, String url, int radius, int placeholderDrawableResId) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new GlideRoundTransform(context, radius))
                .placeholder(placeholderDrawableResId)
                .into(imageView);
    }


    /**
     * Fill an ImageView with a picture from an http link using Glide.
     * 填写使用HTTP链接图片到ImageView
     *
     * @param context                  the Context for where to load
     * @param imageView                the ImageView to fill with an image
     * @param imageUrl                 the image url from which Glide should download and cache the image
     * @param placeholderDrawableResId the resource id of the image that should be used as a placeholder image
     */
    public static void urlToImageView(Context context, ImageView imageView, String imageUrl,
                                      int placeholderDrawableResId) {
        urlToImageView(context, imageView, imageUrl, placeholderDrawableResId, false);
    }

    /**
     * 填写url图片到自定义圆角ImageView
     *
     * @param context
     * @param imageView
     * @param imageUrl
     * @param placeholderDrawableResId
     */
    public static void urlToCircleImageView(Context context, ImageView imageView, String imageUrl,
                                            int placeholderDrawableResId) {
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .placeholder(placeholderDrawableResId)
                .into(imageView);
    }

    public static void urlToCircleImageView(Context context, ImageView imageView, String imageUrl,
                                            Drawable placeholderDrawableResId) {
        Glide.with(context)
                .load(imageUrl)
                .dontAnimate()
                .placeholder(placeholderDrawableResId)
                .into(imageView);
    }

    /***
     * 加载本地图片
     * @param context
     * @param imageView
     * @param imagePath
     */
    public static void pathToImageView(Context context, ImageView imageView, String imagePath) {

        if (context == null || imageView == null) {
            return;
        }
        Glide.with(context).load(Uri.fromFile(new File(imagePath)))
                .into(imageView);
    }

    /**
     * Fill an ImageView with a picture from an Http link using Glide.
     * 填写使用HTTP链接图片到ImageView
     * 是否仅使用缓存加载图片或允许下载
     *
     * @param context                  the Context for where to load
     * @param imageView                the ImageView to fill with an image
     * @param imageUrl                 the image url from which Glide should download and cache the image
     * @param placeholderDrawableResId the resource id of the image that should be used as a placeholder image
     * @param useCacheOnly             whether to only use the cache to load the pictures or allow downloading the
     *                                 picture if the picture is not found in the cache.
     */
    public static void urlToImageView(Context context, ImageView imageView, String imageUrl,
                                      int placeholderDrawableResId, boolean useCacheOnly) {
        if (context == null || imageView == null) {
            return;
        }
        String[] urls = imageUrl.split("\\.");
        if (urls.length == 0){
            return;
        }
        if (useCacheOnly) {
            DrawableTypeRequest<String> load = Glide.with(context)
                    .using(cacheOnlyStreamLoader)
                    .load(imageUrl);

            if ("bmp".equals(urls[urls.length-1])) {
                load.asBitmap();
            }
            load.placeholder(placeholderDrawableResId)
                    .thumbnail(0.1f)
                    .into(imageView);
            return;
        }
        DrawableTypeRequest<String> load = Glide.with(context)
                .load(imageUrl);
        if ("bmp".equals(urls[urls.length-1])) {
            load.asBitmap();
        }
        load.placeholder(placeholderDrawableResId)
                .thumbnail(0.1f)
                .into(imageView);
    }


    private static final StreamModelLoader<String> cacheOnlyStreamLoader = new StreamModelLoader<String>() {
        @Override
        public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
            return new DataFetcher<InputStream>() {
                @Override
                public InputStream loadData(Priority priority) throws Exception {
                    throw new IOException();
                }

                @Override
                public void cleanup() {

                }

                @Override
                public String getId() {
                    return model;
                }

                @Override
                public void cancel() {

                }
            };
        }
    };

}
