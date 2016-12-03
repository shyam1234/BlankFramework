package com.malviya.blankframework.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.malviya.blankframework.R;

/**
 * Created by 23508 on 11/30/2016.
 */

public class CustomDialogbox extends Dialog {

    public final static int TYPE_OK = 1;
    public final static int TYPE_YES_NO = 2;
    public final static int TYPE_YES_NO_SKIP = 3;
    private final Context mContext;
    private int mType;
    private Button mBtnOK, mBtnYes, mBtnNo, mBtnSkip;
    private TextView mTextViewMessage;

    public CustomDialogbox(Context context, int pType) {
        super(context);
        mType = pType;
        mContext = context;
        //for background transparency
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        switch (mType) {
            case TYPE_OK:
                setContentView(R.layout.dialog_ok);
                mBtnOK = (Button)findViewById(R.id.btnOK);
                mTextViewMessage = (TextView)findViewById(R.id.textview_message);
                break;
            case TYPE_YES_NO:
                setContentView(R.layout.dialog_yes_no);
                mBtnYes = (Button)findViewById(R.id.btnYes);
                mBtnNo = (Button)findViewById(R.id.btnNo);
                mTextViewMessage = (TextView)findViewById(R.id.textview_message);
                break;
            case TYPE_YES_NO_SKIP:
                setContentView(R.layout.dialog_yes_no_skip);
                mBtnYes = (Button)findViewById(R.id.btnYes);
                mBtnNo = (Button)findViewById(R.id.btnNo);
                mBtnSkip = (Button)findViewById(R.id.btnSkip);
                mTextViewMessage = (TextView)findViewById(R.id.textview_message);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public Button getBtnOK() {
        return mBtnOK;
    }

    public Button getBtnYes() {
        return mBtnYes;
    }

    public Button getBtnNo() {
        return mBtnNo;
    }

    public void setMessage(String msg) {
         mTextViewMessage.setText(msg);
    }


}
