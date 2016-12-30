package com.malviya.blankframework.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.malviya.blankframework.R;
import com.malviya.blankframework.application.MyApplication;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 26-11-2016.
 */

public class Utils {

    private static Context mContext = MyApplication.getInstance().getApplicationContext();

    public static boolean validateUserName(EditText pEditText) {
        if (pEditText != null && !pEditText.getText().toString().equals("")) {
            return validateEmail(pEditText);
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_username_2));
            vibrateEditText(pEditText);
        }
        return false;
    }

    public static boolean validatePassword(EditText pEditText) {
        if (pEditText != null && !pEditText.getText().toString().equals("")) {
            if (pEditText.getText().toString().length() >= 4 && pEditText.getText().toString().length() <= 12) {
                return true;
            } else {
                pEditText.setError(mContext.getResources().getString(R.string.msg_validate_password_3));
                vibrateEditText(pEditText);
            }
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_password_2));
            vibrateEditText(pEditText);
        }
        return false;
    }

    // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public static boolean validateEmail(EditText pEditText) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(pEditText.getText().toString());
        if (matcher.matches()) {
            return true;
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_emai));
            vibrateEditText(pEditText);
            return false;
        }

    }

    public static boolean isContainSpecialChar(EditText pEditText) {
        if (pEditText.getText().toString().matches("[a-zA-Z0-9]*")) {
            return false;
        } else {
            pEditText.setError(mContext.getResources().getString(R.string.msg_validate_password_1));
            vibrateEditText(pEditText);
            return true;
        }

    }

    public static void vibrateEditText(EditText pEditText) {
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.vibrate);
        pEditText.startAnimation(shake);
    }


    public static void animRightToLeft(Activity pActivity) {
        if (pActivity instanceof Activity)
            pActivity.overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public static void animLeftToRight(Activity pActivity) {
        if (pActivity instanceof Activity)
            pActivity.overridePendingTransition(R.anim.left, R.anim.right);
    }


    public static String encodeToString(String text) {
        byte[] data = new byte[0];
        try {
            data = text.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String decodeToString(String base64) {
        try {
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public static boolean validateChangePassword(EditText pEditTextEmail, EditText pEditTextNewPassword, EditText pEditTextConfirmPassword) {
        if (validateEmail(pEditTextEmail)
                && validatePassword(pEditTextNewPassword)
                && validatePassword(pEditTextConfirmPassword)) {
            // if (!pEditTextEmail.getText().toString().equals(pEditTextNewPassword.getText().toString())) {
            if (pEditTextNewPassword.getText().toString().equals(pEditTextConfirmPassword.getText().toString())) {
                return true;
            } else {
                pEditTextConfirmPassword.setError(mContext.getResources().getString(R.string.msg_validate_new_password));
            }
//            } else {
//                pEditTextNewPassword.setError(mContext.getResources().getString(R.string.msg_validate_new_password1));
//            }
        }
        return false;
    }
}
