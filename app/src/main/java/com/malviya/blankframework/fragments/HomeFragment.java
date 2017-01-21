package com.malviya.blankframework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.HomeAdapter;
import com.malviya.blankframework.constant.Contant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableParentStudentMenuDetails;
import com.malviya.blankframework.models.DashboardCellDataHolder;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            R.drawable.gallery,R.drawable.feedback, R.drawable.fee};

    private GridView mGridViewCell;
    private HomeAdapter mAdapter;
    private ArrayList<DashboardCellDataHolder> mCellList;
    private ArrayList<TableStudentDetailsDataModel> mUniver;
    private ImageView mImageViewUnivercityLogo;
    private TextView mTextViewUnivercityText;

    public HomeFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        fetchDataFromWS();
    }

    private void init() {
        mCellList = new ArrayList<>();

    }


    private void fetchDataFromWS() {

        //need to fetch data from DB WRT to above parameters
        //on the basis of parent id and student id
        if(UserInfo.parentId!=null && UserInfo.studentId!=null){
            //--for header
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_CONTENT_TYPE, "application/json");
            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTime());
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            //--for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_PARENTID, UserInfo.parentId);
            body.put(WSContant.TAG_STUDENTID, UserInfo.studentId);

            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEHOME, header, body, WSContant.TAG_GETMOBILEHOME, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //--parsing logic------------------------------------------------------------------
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_LOGIN);
                    LoginDataModel holder = ((LoginDataModel) obj.getModel());
                    AppLog.log(TAG, "getPhoneNumber: " + holder.data.PhoneNumber);
//                    try {
//                        AppLog.log(TAG, "getPhoneNumber by global model: " + ((LoginDataModel) ModelFactory.getInstance().getModel(LoginDataModel.KEY)).data.PhoneNumber);
//                    } catch (ModelException e) {
//                        AppLog.errLog(TAG,"Exception from "+e.getMessage());
//                    }
                    //--------------------------------------------------------------------
                    AppLog.log(TAG, "parentId: " + holder.data.UserId);
                    AppLog.log(TAG, "parentName: " + holder.data.UserName);
                    for (LoginDataModel.ParentStudentAssociation parentStudentAsso : holder.parentStudentAssociationArrayList) {
                        AppLog.log(TAG, "parentName: " + parentStudentAsso.IsDefault);
                        if (parentStudentAsso.IsDefault) {
                            AppLog.log(TAG, "default student: " + parentStudentAsso.StudentId);
                            UserInfo.studentId = "" + parentStudentAsso.StudentId;
                        }
                    }
                    UserInfo.parentId = "" + holder.data.UserId;
                    UserInfo.parentName = holder.data.UserName;
                    //--------------------------------------------------------------------
                }

                @Override
                public void onErrorResponse(VolleyError response) {

                }
            });


        }else{
            AppLog.errLog("HomeFragment","Empty field parentId "+UserInfo.parentId);
            AppLog.errLog("HomeFragment","Empty field studentId "+UserInfo.studentId);
        }
        //----------------------------------------------------------
        TableParentStudentMenuDetails table = new TableParentStudentMenuDetails();
        table.openDB(getContext());
        mCellList = table.getHomeFragmentData(UserInfo.parentId, UserInfo.studentId);
        table.closeDB();

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
        initView();

    }

    private void initView() {
        mAdapter = new HomeAdapter(getContext(), mCellList);
        mGridViewCell = (GridView) getView().findViewById(R.id.gridview_dashboard);
        mGridViewCell.setAdapter(mAdapter);
        mImageViewUnivercityLogo = (ImageView) getView().findViewById(R.id.imgview_uni_logo);
        mTextViewUnivercityText = (TextView) getView().findViewById(R.id.textview_uni_header_name);
        mGridViewCell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = ((TextView) view.findViewById(R.id.textview_dashboard_cell_name));
                AppLog.log("onItemClick name", textView.getText().toString());
                switch (textView.getText().toString()) {
                    case Contant.TAG_NOTICEBOARD:
                        Utils.navigateFragment(getFragmentManager(), new NoticeboardFragment(), NoticeboardFragment.TAG);
                        break;
                    case Contant.TAG_ATTENDANCE:
                        Utils.navigateFragment(getFragmentManager(), new AttendanceFragment(), AttendanceFragment.TAG);
                        break;
                    case Contant.TAG_DIARY:
                        Utils.navigateFragment(getFragmentManager(), new DiaryFragment(), DiaryFragment.TAG);
                        break;
                    case Contant.TAG_EVENTS:
                        Utils.navigateFragment(getFragmentManager(), new EventsFragment(), EventsFragment.TAG);
                        break;
                    case Contant.TAG_FEE:
                        Utils.navigateFragment(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                        break;
                    case Contant.TAG_GALLERY:
                        Utils.navigateFragment(getFragmentManager(), new GalleryFragment(), GalleryFragment.TAG);
                        break;
                    case Contant.TAG_FEEDBACK:
                        Utils.navigateFragment(getFragmentManager(), new FeedbackFragment(), FeedbackFragment.TAG);
                        break;
                    case Contant.TAG_MESSAGE:
                        Utils.navigateFragment(getFragmentManager(), new MessageFragment(), MessageFragment.TAG);
                        break;
                    case Contant.TAG_HOMEWORK:
                        Utils.navigateFragment(getFragmentManager(), new HomeworkFragment(), HomeworkFragment.TAG);
                        break;
                }

            }
        });

        if (mCellList.size() > 0) {
            //pp mImageViewUnivercityLogo.setImageBitmap(Utils.getImage(mCellList.get(0).getUniversity_image_url()));
            mTextViewUnivercityText.setText(mCellList.get(0).getUniversity_name());
        }
    }
}
