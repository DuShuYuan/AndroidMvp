package com.dsy.mvp.component;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * 加载图片
 */
public class ImageLoader {

    /**
     * 加载图片
     */
    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircle(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).apply(RequestOptions.circleCropTransform()).into(iv);
    }
    /**
     * 加载圆角图片
     */
    public static void loadRoundCorner(Context context, String url, ImageView iv, int dpRound) {
        Glide.with(context).load(url).transform(new RoundedCorners(ConvertUtils.dp2px(dpRound))).into(iv);
    }
}
