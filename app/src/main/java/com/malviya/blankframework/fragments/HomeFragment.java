package com.malviya.blankframework.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.adapters.HomeAdapter;
import com.malviya.blankframework.constant.Contant;
import com.malviya.blankframework.database.TableParentStudentMenuDetails;
import com.malviya.blankframework.models.DashboardCellDataHolder;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.RenderImageByPicasso;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFramgent";
    public static int[] mMenuColor = new int[]{R.color.colorGreen, R.color.colorDarkYellow, R.color.colorLightVallet,
            R.color.colorSkyBlue, R.color.colorDarkPink, R.color.colorDarkblue,
            R.color.colorLightParrot, R.color.colorDarkVal, R.color.colorLightOran};
    public static int[] mMenuImage = new int[]{R.drawable.noticeboard, R.drawable.attendance, R.drawable.homework,
            R.drawable.diary, R.drawable.messages, R.drawable.events,
            R.drawable.gallery, R.drawable.feedback, R.drawable.fee};

    private GridView mGridViewCell;
    private HomeAdapter mAdapter;
    private ArrayList<DashboardCellDataHolder> mCellList;
    private ArrayList<TableStudentDetailsDataModel> mUniver;
    private ImageView mImageViewUnivercityLogo;
    private TextView mTextViewUnivercityText;
    private LinearLayout mLinearHolder;


    public HomeFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        AppLog.log("HomeFragment", "onCreate");
    }

    private void init() {
        mCellList = new ArrayList<>();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_home, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.log("HomeFragment ", "bindDataWithParentStudentMenuDetailsDataModel: : onActivityCreated");
        initView();

        //fetchDataFromWS();
        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.what) {
                    case 1:
                        //----------------------------------------------------------------------
                        final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
                        table.openDB(getContext());
                        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
                        table.closeDB();
                        AppLog.log("HomeFragment ", "mCellList mCellList " + mCellList.size());
                        AppLog.log("HomeFragment ", "bindDataWithParentStudentMenuDetailsDataModel: handleMessage : onActivityCreated");
                        DashboardActivity.mHandler.removeMessages(1);
                        mLinearHolder.setVisibility(View.GONE);
                        AppLog.log(TAG, "UserInfo.studentId :" + UserInfo.studentId);
                        initView();
                        //----------------------------------------------------------------------
                        // fetchDataFromWS();
                        return true;
                }
                return false;
            }
        });
    }


//    private void fetchDataFromWS() {
//        if (UserInfo.parentId != -1 && UserInfo.studentId != -1) {
//            //--for header
//            Map<String, String> header = new HashMap<>();
//            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
//            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTime());
//            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
//            //--for body
//            Map<String, String> body = new HashMap<>();
//            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
//            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);
//            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEHOME, header, body, WSContant.TAG_GETMOBILEHOME, new IWSRequest() {
//                @Override
//                public void onResponse(String response) {
//                    AppLog.log(TAG, "onResponse +++ " + response.toString());
//                    AppLog.log("HomeFragment ", "bindDataWithParentStudentMenuDetailsDataModel: : onResponse");
//                    initTableAndDisplay(response);
//                }
//
//                @Override
//                public void onErrorResponse(VolleyError response) {
//                    initTableAndDisplay(null);
//                    AppLog.log("HomeFragment ", "bindDataWithParentStudentMenuDetailsDataModel: : onErrorResponse");
//                }
//            });
//
//
//        } else {
//            AppLog.errLog("HomeFragment", "Empty field parentId " + UserInfo.parentId);
//            AppLog.errLog("HomeFragment", "Empty field studentId " + UserInfo.studentId);
//        }
//    }
//
//    private void initTableAndDisplay(String response) {
//
//        if (response != null) {
//            ParseResponse obj = new ParseResponse(response, GetMobileHomeDataHolder.class, ModelFactory.MODEL_GETMOBILEHOME);
//            GetMobileHomeDataHolder holder = ((GetMobileHomeDataHolder) obj.getModel());
//            for (LoginDataModel.ParentStudentAssociation parentStudentAsso : holder.parentStudentAssociationArrayList) {
//                AppLog.log(TAG, "IsDefault: " + parentStudentAsso.IsDefault);
//                if (parentStudentAsso.IsDefault) {
//                    UserInfo.studentId = parentStudentAsso.StudentId;
//                }
//            }
//
//            for (LoginDataModel.University university : holder.universityArrayList) {
//                UserInfo.univercityId = university.UniversityId;
//            }
//
//            bindDataWithParentStudentMenuDetailsDataModel(holder);
//        }
//        //----------------------------------------------------------------------
//        final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
//        table.openDB(getContext());
//        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
//        table.closeDB();
//        AppLog.log("HomeFragment ", "mCellList mCellList " + mCellList.size());
//        initView();
//        mLinearHolder.setVisibility(View.VISIBLE);
//    }

//    private void bindDataWithParentStudentMenuDetailsDataModel(GetMobileHomeDataHolder holder) {
//        try {
//            AppLog.log("HomeFragment ", "bindDataWithParentStudentMenuDetailsDataModel");
//            ArrayList<TableParentStudentMenuDetailsDataModel> list = new ArrayList<TableParentStudentMenuDetailsDataModel>();
//            for (LoginDataModel.ParentStudentMenuDetails model : holder.ParentStudentMenuDetailsArrayList) {
//                //-- assign value to model
//                TableParentStudentMenuDetailsDataModel obj = new TableParentStudentMenuDetailsDataModel();
//                obj.setAlert_count(model.ColumnCount);
//                obj.setIsActive(model.IsActive);
//                obj.setMenuCode(model.SubscriptionCode);
//                obj.setParentId(model.ParentId);
//                obj.setStudentId(model.StudentId);
//                obj.setUniversityId(model.UniversityId);
//                list.add(obj);
//            }
//            //saving into database
//            final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
//            table.openDB(getContext());
//            table.insert(list);
//        } catch (Exception e) {
//            AppLog.errLog("HomeFragment bindDataWithParentStudentMenuDetailsDataModel", e.getMessage());
//        }
//    }


    private void initView() {
        if (getView() == null) {
            return;
        }

        mLinearHolder = (LinearLayout) getView().findViewById(R.id.linearlayout);
        mLinearHolder.setVisibility(View.GONE);
        //----------------------------------------------------------------------
        final TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
        table.openDB(getContext());
        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
        table.closeDB();
        AppLog.log("HomeFragment ", "mCellList mCellList " + mCellList.size());
        //----------------------------------------------------------------------
        mLinearHolder.setVisibility(View.GONE);
        mAdapter = new HomeAdapter(getContext(), mCellList);
        mGridViewCell = (GridView) getView().findViewById(R.id.gridview_dashboard);
        mGridViewCell.setAdapter(mAdapter);
        mImageViewUnivercityLogo = (ImageView) getView().findViewById(R.id.imgview_uni_logo);
        if (mCellList.size() > 0)
            RenderImageByPicasso.setCircleImageByPicasso(getContext(), mCellList.get(0).getUniversity_url(), mImageViewUnivercityLogo);
        //RenderImageByUIL.getInstance(getContext()).setImageByURL(UserInfo.university_logo_url, mImageViewUnivercityLogo, R.drawable.logo, R.drawable.loader);
        mTextViewUnivercityText = (TextView) getView().findViewById(R.id.textview_uni_header_name);
        mGridViewCell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = ((TextView) view.findViewById(R.id.textview_dashboard_cell_name));
                AppLog.log("onItemClick menu name: ", textView.getText().toString());
                AppLog.log("onItemClick getTag", textView.getTag().toString());
                switch (textView.getTag().toString()) {
                    case Contant.TAG_NOTICEBOARD:
                        Utils.navigateFragmentMenu(getFragmentManager(), new NoticeboardFragment(), NoticeboardFragment.TAG);
                        break;
                    case Contant.TAG_ATTENDANCE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new AttendanceFragment(), AttendanceFragment.TAG);
                        break;
                    case Contant.TAG_DIARY:
                        Utils.navigateFragmentMenu(getFragmentManager(), new DiaryFragment(), DiaryFragment.TAG);
                        break;
                    case Contant.TAG_EVENTS:
                        Utils.navigateFragmentMenu(getFragmentManager(), new EventsFragment(), EventsFragment.TAG);
                        break;
                    case Contant.TAG_FEE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                        break;
                    case Contant.TAG_GALLERY:
                        Utils.navigateFragmentMenu(getFragmentManager(), new GalleryFragment(), GalleryFragment.TAG);
                        break;
                    case Contant.TAG_FEEDBACK:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeedbackFragment(), FeedbackFragment.TAG);
                        break;
                    case Contant.TAG_MESSAGE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new MessageFragment(), MessageFragment.TAG);
                        break;
                    case Contant.TAG_HOMEWORK:
                        Utils.navigateFragmentMenu(getFragmentManager(), new HomeworkFragment(), HomeworkFragment.TAG);
                        break;
                    case Contant.TAG_NEWS:
                        Utils.navigateFragmentMenu(getFragmentManager(), new NewsFragment(), NewsFragment.TAG);
                        break;
                }
            }
        });

        if (mCellList.size() > 0) {
            mTextViewUnivercityText.setText(mCellList.get(0).getUniversity_name());
        }
    }
}
