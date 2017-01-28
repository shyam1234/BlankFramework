package com.malviya.blankframework.utils;

import android.content.Context;
import android.widget.ImageView;

import com.malviya.blankframework.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Admin on 26-11-2016.
 */

public class RenderImageByPicasso {
    /**
     * this method uses to render image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     * @param placeHolder
     * @param errorHolder
     * @param resize
     */
    public static void setImageByPicasso(Context pContext, String imgURL, ImageView imageView, int placeHolder, int errorHolder, int resize) {
        if (resize == 0) {
            resize = 50; //default resize size
        }
        if(imgURL!=null) {
            Picasso.with(pContext)
                    .load(imgURL)
                    .resize(resize, resize)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .error(errorHolder)
                    .into(imageView);
        }else {
            imageView.setImageResource(R.drawable.profile_logo);
        }
    }

    /**
     * this method uses to render image by Picasso
     *
     * @param pContext
     * @param imgURL
     * @param imageView
     */
    public static void setImageByPicasso(Context pContext, String imgURL, ImageView imageView) {
        if(imgURL!=null) {
            Picasso.with(pContext)
                    .load(imgURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView);
        }else{
            imageView.setImageResource(R.drawable.profile_logo);
        }
    }


    /**
     * this method renders circle image by using picasso
     *
     * @param pContext
     * @param url
     * @param imageView
     */
    public static void setCircleImageByPicasso(Context pContext, String url, ImageView imageView) {
        AppLog.log("setCircleImageByPicasso: ", url);
        if(url!=null) {
            Picasso.with(pContext)
                    .load(url)
                    .fit()
                    .transform(new ImageTransform())
                    .into(imageView);
        }else{
            imageView.setImageResource(R.drawable.logo);
        }
    }

    /**
     * this method renders square image by Picasso
     * @param pContext
     * @param url
     * @param imageView
     */
    public static void setSquareImageByPicasso(Context pContext, String url, ImageView imageView) {
        if(url!=null) {

            Picasso.with(pContext)
                    .load(url)
                    .fit()
                    .transform(new SquareImageTransform())
                    .into(imageView);
        }else{
            imageView.setImageResource(R.drawable.profile_logo);
        }
    }


}
