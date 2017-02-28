package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.NewsDetails;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.models.TableDocumentMasterDataModel;
import com.malviya.blankframework.utils.GetPicassoImage;

import java.util.ArrayList;

/**
 * Created by Admin on 25-02-2017.
 */

public class CustomPagerAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<TableDocumentMasterDataModel> mResources;
    private View.OnClickListener mListerner;



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
        return view == ((LinearLayout) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        if(mResources.get(position).getMediatype().equalsIgnoreCase(WSContant.TAG_VIDEO)) {
            GetPicassoImage.getImage(mContext, "http://steveladdmusic.com/wp-content/themes/americanaura/assets/images/default-video-thumbnail.jpg", imageView);
        }else if(mResources.get(position).getMediatype().equalsIgnoreCase(WSContant.TAG_IMAGE)) {
            GetPicassoImage.getImage(mContext, mResources.get(position).getDocumentpath(), imageView);
        }
        container.addView(itemView);
        imageView.setOnClickListener(mListerner);
        imageView.setTag(position);
        return itemView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    public static class NewsCutomPagerHolder{

    }
}
