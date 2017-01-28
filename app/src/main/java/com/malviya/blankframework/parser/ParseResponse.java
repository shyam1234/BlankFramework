package com.malviya.blankframework.parser;

import com.google.gson.Gson;
import com.malviya.blankframework.interfaces.IModel;
import com.malviya.blankframework.models.GetMobileHomeDataHolder;
import com.malviya.blankframework.models.LanguageArrayDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;

/**
 * Created by Admin on 30-12-2016.
 */

public class ParseResponse {

    private final String TAG = "ParseResponse";
    private final String mKey;
    private String mRespose;
    private Gson mGson;
    private IModel mModel;
    private Class<? extends IModel> mModelClass;

    public ParseResponse(String response, Class<? extends IModel> pModelClass, String key) {
        mRespose = response;
        mGson = new Gson();
        mKey = key;
        mModelClass = pModelClass;
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
                mModel = mGson.fromJson(mRespose, GetMobileHomeDataHolder.class);
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
