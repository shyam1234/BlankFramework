package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.models.TableDocumentMasterDataModel;
import com.malviya.blankframework.utils.GetPicassoImage;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Admin on 25-02-2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<TableDocumentMasterDataModel> mResources;
    private View.OnClickListener mListerner;
    private static final String TEST_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";


    public CustomPagerAdapter(Context context, ArrayList<TableDocumentMasterDataModel> documents, View.OnClickListener pListner) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = documents;
        mListerner = pListner;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== ((RelativeLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        JCVideoPlayerStandard video = (JCVideoPlayerStandard) itemView.findViewById(R.id.videoplayer);
        imageView.setVisibility(View.GONE);
        video.setVisibility(View.GONE);
        if(mResources.get(position).getMediatype().equalsIgnoreCase(WSContant.TAG_VIDEO)) {
            GetPicassoImage.getImage(mContext, "", imageView);
            video.setVisibility(View.VISIBLE);
            video.setUp(mResources.get(position).getFileURL(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,mResources.get(position).getDocumentname());
            //video.setUp(TEST_URL, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,mResources.get(position).getDocumentname());
            GetPicassoImage.getImage(mContext,"http://steveladdmusic.com/wp-content/themes/americanaura/assets/images/default-video-thumbnail.jpg", video.thumbImageView);
        }else if(mResources.get(position).getMediatype().equalsIgnoreCase(WSContant.TAG_IMAGE)) {
            GetPicassoImage.getImage(mContext, mResources.get(position).getFileURL(), imageView);
            imageView.setVisibility(View.VISIBLE);
        }

        container.addView(itemView);
        imageView.setOnClickListener(mListerner);
        imageView.setTag(position);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

}
