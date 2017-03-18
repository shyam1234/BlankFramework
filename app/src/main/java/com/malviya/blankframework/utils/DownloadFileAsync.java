package com.malviya.blankframework.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.malviya.blankframework.constant.WSContant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.malviya.blankframework.utils.DownloadFileAsync.TAG;

/**
 * Created by Admin on 18-03-2017.
 */

public class DownloadFileAsync extends AsyncTask<String, String, String> {
    public static final String TAG = "DownloadFileAsync";
    private final Context mContext;
    private final String mFolderName;

    public DownloadFileAsync(Activity pContext, String pFolderName) {
        mContext = pContext;
        mFolderName = pFolderName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Utils.showProgressBar(mContext);
    }

    @Override
    protected void onPostExecute(String pFileName) {
        Utils.dismissProgressBar();
        AppLog.log("Response", pFileName);
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + mFolderName + "/" + pFileName);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            mContext.startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(mContext, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {

    }

    @Override
    protected String doInBackground(String... strings) {
        String fileUrl = strings[0] + strings[1];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        String mFileName = strings[1] + ".pdf";  // -> maven.pdf
        try {
            File mFolder = new File(Environment.getExternalStorageDirectory().toString(), mFolderName);
            mFolder.mkdir();
            File pdfFile = new File(mFolder, mFileName);
            try {
                pdfFile.createNewFile();
            } catch (IOException e) {
                AppLog.errLog(TAG, e.getMessage());
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            Utils.dismissProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mFileName;
    }
}


class FileDownloader {
    private static final int MEGABYTE = 1024 * 1024;
    public static void downloadFile(String fileUrl, File directory) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty(WSContant.TAG_TOKEN, UserInfo.authToken);
            urlConnection.setRequestProperty(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-----------------------------------
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();
            //-----------------------------------
            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            AppLog.errLog(TAG, e.getMessage());
        } catch (MalformedURLException e) {
            AppLog.errLog(TAG, e.getMessage());
        } catch (IOException e) {
            AppLog.errLog(TAG, e.getMessage());
        }
    }
}
