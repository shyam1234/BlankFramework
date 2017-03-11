package com.malviya.blankframework.parser;

import com.google.gson.Gson;
import com.malviya.blankframework.interfaces.IModel;
import com.malviya.blankframework.models.GetMobileDetailsDataModel;
import com.malviya.blankframework.models.GetMobileHomeDataModel;
import com.malviya.blankframework.models.GetMobileMenuDataModel;
import com.malviya.blankframework.models.LanguageArrayDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.NewsDetailsCommentLikeDataModel;
import com.malviya.blankframework.models.TableTimeTableDetailsDataModel;

/**
 * Created by Admin on 30-12-2016.
 */

public class ParseResponse {

    private final String TAG = "ParseResponse";
    private final String mKey;
    private String mRespose;
    private Gson mGson;
    private IModel mModel;
   // private Class<? extends IModel> mModelClass;

    public ParseResponse(String response, Class<? extends IModel> pModelClass, String key) {
        mRespose = response;
        mGson = new Gson();
        mKey = key;
       // mModelClass = pModelClass;
        doParse();
    }


    /**
     * add model class here to parse response through gson
     */
    private void doParse() {
        switch (mKey) {
            case ModelFactory.MODEL_LOGIN:
                mModel = mGson.fromJson(mRespose, LoginDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_LANG:
                mModel = mGson.fromJson(mRespose, LanguageArrayDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEHOME:
                mModel = mGson.fromJson(mRespose, GetMobileHomeDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEMENU:
                mModel = mGson.fromJson(mRespose, GetMobileMenuDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_GETMOBILEDETAILS:
                mModel = mGson.fromJson(mRespose, GetMobileDetailsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_NEWS_DETAILS_COMMENTS_LIKE:
                mModel = mGson.fromJson(mRespose, NewsDetailsCommentLikeDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            case ModelFactory.MODEL_TIMETABLEDETAILS:
                mModel = mGson.fromJson(mRespose, TableTimeTableDetailsDataModel.class);
                ModelFactory.getInstance().register(mKey, mModel);
                break;
            default:
                break;
        }
    }

    public IModel getModel() {
        return mModel;
    }
}
