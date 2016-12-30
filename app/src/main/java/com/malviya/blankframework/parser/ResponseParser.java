package com.malviya.blankframework.parser;

import com.google.gson.Gson;
import com.malviya.blankframework.Exceptions.ModelException;
import com.malviya.blankframework.interfaces.IModel;
import com.malviya.blankframework.models.LoginDataHolder;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.utils.AppLog;

/**
 * Created by Admin on 30-12-2016.
 */

public class ResponseParser {

    private final String TAG = "ResponseParser";
    private String mRespose;
    private Gson mGson;
    private IModel model;

    public ResponseParser(String response) {
        mRespose = response;
        mGson = new Gson();
    }

    public IModel getParceModel(IModel pModel) throws ModelException {
        try {
            final String key = pModel.KEY;
            switch (key) {
                case LoginDataHolder.KEY:
                    AppLog.log(TAG,"+++LoginDataHolder+++");
                    ModelFactory.getInstance().registerModel(key, parseLoginData());
                    break;
                default:
                    AppLog.log(TAG,"+++defaultdefault default+++");
                    break;
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, e.getMessage());
        }
        return ModelFactory.getInstance().getModel(pModel.KEY);
    }

    private IModel parseLoginData() {
        return (model = mGson.fromJson(mRespose, LoginDataHolder.class));
    }

    public IModel getModel(){
        return model;
    }
}
