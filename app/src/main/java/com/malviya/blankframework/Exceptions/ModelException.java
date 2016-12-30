package com.malviya.blankframework.Exceptions;

import com.malviya.blankframework.utils.AppLog;

/**
 * Created by Admin on 30-12-2016.
 */

public class ModelException extends Exception {

    public ModelException(String exceptionMesg) {
        AppLog.errLog("ModelException", exceptionMesg);
    }
}
