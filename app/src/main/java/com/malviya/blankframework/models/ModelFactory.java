package com.malviya.blankframework.models;

import com.malviya.blankframework.exceptions.ModelException;
import com.malviya.blankframework.R;
import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.interfaces.IModel;

import java.util.HashMap;

/**
 * Created by Admin on 30-12-2016.
 */

public class ModelFactory {
    private volatile static ModelFactory mInstance;
    public final static String MODEL_LOGIN = "LoginDataHolder";
    public final static String MODEL_LANG="LanguageArrayDataModel";

    private HashMap<String, IModel> mHashMapMode;

    private ModelFactory() {
        mHashMapMode = new HashMap<>();
    }

    public static ModelFactory getInstance() {
        synchronized (ModelFactory.class) {
            if (mInstance == null) {
                mInstance = new ModelFactory();
            }
        }
        return mInstance;
    }

    public IModel getModel(String key) throws ModelException {
        IModel model = null;
        if ((model = mHashMapMode.get(key)) != null) {
            return model;
        }
        throw new ModelException(MyApplication.getInstance().getApplicationContext().getResources().getString(R.string.msg_error_model));
    }

    public void register(String key, IModel model) {
        mHashMapMode.put(key, model);
    }

    public void unRegister(String key) {
        mHashMapMode.remove(key);
    }

    public void unRegisterAll() {
        mHashMapMode.clear();
    }


}
