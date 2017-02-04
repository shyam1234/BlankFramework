package com.malviya.blankframework.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableParentStudentMenuDetails;
import com.malviya.blankframework.models.GetMobileHomeDataHolder;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableParentStudentMenuDetailsDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 26-11-2016.
 */

public class Utils {

    private static final String TAG = Utils.class.getName();
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


    public static String getCurrTime() {
        Calendar c = Calendar.getInstance();
//      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        AppLog.log("formattedDate", formattedDate);
        return formattedDate;

    }

    public static void showProgressBar(int progress, CircularProgressBar circularProgressBar) {

        circularProgressBar.setColor(ContextCompat.getColor(circularProgressBar.getContext(), R.color.progressBarColor));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(circularProgressBar.getContext(), R.color.backgroundProgressBarColor));
        circularProgressBar.setProgressBarWidth(6);
        circularProgressBar.setBackgroundProgressBarWidth(9);
        int animationDuration = 2500; // 2500ms = 2,5s
        circularProgressBar.setProgressWithAnimation(progress, animationDuration); // Default duration = 1500ms
    }


    public static void navigateFragment(FragmentManager fragmentManager, Fragment fragment, String TAG) {
        try {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.framelayout_holder, fragment);
            ft.addToBackStack(TAG);
            ft.commit();
            ft.setCustomAnimations(R.anim.left, R.anim.right);
        } catch (Exception e) {
            AppLog.errLog("navigateFragment", e.getMessage());
        }
    }


    public static void navigateFragmentMenu(FragmentManager fragmentManager, Fragment fragment, String TAG) {
        try {
            AppLog.log("navigateFragmentMenu ","navigateFragmentMenu called on menu clicked "+TAG);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.framelayout_holder, fragment);
            ft.addToBackStack(TAG);
            ft.commit();
            ft.setCustomAnimations(R.anim.left, R.anim.right);
        } catch (Exception e) {
            AppLog.errLog("navigateFragmentMenu", e.getMessage());
        }
    }


    public static Bitmap getImage(String university_image_url) {
        return null;
    }


//    public static String getLastRetrivedTime() {
//        try {
//            SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(WSContant.TAG_SHAREDPREF_NAME, Context.MODE_PRIVATE);
//            String lastRetrivedTime = getCurrTime();
//            if ((lastRetrivedTime = sp.getString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, null)) != null) {
//                return lastRetrivedTime;
//            } else {
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, lastRetrivedTime);
//                editor.commit();
//            }
//            return lastRetrivedTime;
//        } catch (Exception e) {
//            AppLog.errLog("getLastRetrivedTime", e.getMessage());
//        } finally {
//            return "";
//        }
//    }

    public static String getLastRetrivedTime() {
        try {
            if (SharedPreferencesApp.getInstance().getSavedTime() != null) {
                return SharedPreferencesApp.getInstance().getSavedTime();
            } else {
                SharedPreferencesApp.getInstance().saveTime(getCurrTime());
            }
        } catch (Exception e) {
            AppLog.errLog("getLastRetrivedTime", e.getMessage());

        } finally {
            return SharedPreferencesApp.getInstance().getSavedTime();
        }
    }



    public static void updateHomeTableAsPerDefaultChildSelection() {
       // UserInfo.studentId = SharedPreferencesApp.getInstance().getDefaultChildSelection();
        AppLog.log("Utils ","UserInfo.studentId :"+UserInfo.studentId);
        if (UserInfo.parentId != -1 && UserInfo.studentId != -1) {
            //--for header
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTime());
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEHOME, header, body, WSContant.TAG_GETMOBILEHOME, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    AppLog.log(TAG, "onResponse +++ " + response.toString());
                    AppLog.log("Utils ","bindDataWithParentStudentMenuDetailsDataModel: : onResponse");
                    initTableAndDisplay(response);
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    initTableAndDisplay(null);
                    AppLog.log("Utils ","bindDataWithParentStudentMenuDetailsDataModel: : onErrorResponse");
                }
            });

        } else {
            AppLog.errLog("Utils", "Empty field parentId " + UserInfo.parentId);
            AppLog.errLog("Utils", "Empty field studentId " + UserInfo.studentId);
        }
    }

    private static void initTableAndDisplay(String response) {

        if (response != null) {
            ParseResponse obj = new ParseResponse(response, GetMobileHomeDataHolder.class, ModelFactory.MODEL_GETMOBILEHOME);
            GetMobileHomeDataHolder holder = ((GetMobileHomeDataHolder) obj.getModel());
            for (LoginDataModel.ParentStudentAssociation parentStudentAsso : holder.parentStudentAssociationArrayList) {
                AppLog.log(TAG, "IsDefault: " + parentStudentAsso.IsDefault);
                if (parentStudentAsso.IsDefault) {
                    UserInfo.studentId = parentStudentAsso.StudentId;
                }
            }

            for (LoginDataModel.University university : holder.universityArrayList) {
                UserInfo.univercityId = university.UniversityId;
            }

            bindDataWithParentStudentMenuDetailsDataModel(holder);
        }

    }

    private static void bindDataWithParentStudentMenuDetailsDataModel(GetMobileHomeDataHolder holder) {
        try {
            AppLog.log("Utils ","bindDataWithParentStudentMenuDetailsDataModel");
            ArrayList<TableParentStudentMenuDetailsDataModel> list = new ArrayList<TableParentStudentMenuDetailsDataModel>();
            for (LoginDataModel.ParentStudentMenuDetails model : holder.ParentStudentMenuDetailsArrayList) {
                //-- assign value to model
                TableParentStudentMenuDetailsDataModel obj = new TableParentStudentMenuDetailsDataModel();
                obj.setAlert_count(model.ColumnCount);
                obj.setIsActive(model.IsActive);
                obj.setMenuCode(model.SubscriptionCode);
                obj.setParentId(model.ParentId);
                obj.setStudentId(model.StudentId);
                obj.setUniversityId(model.UniversityId);
                list.add(obj);
            }
            //saving into database
            final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
            table.openDB(MyApplication.getInstance().getApplicationContext());
            table.insert(list) ;
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG+" bindDataWithParentStudentMenuDetailsDataModel", e.getMessage());
        }
    }

}
