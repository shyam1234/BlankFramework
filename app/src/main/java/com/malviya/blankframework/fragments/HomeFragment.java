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

import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.HomeAdapter;
import com.malviya.blankframework.constant.Contant;
import com.malviya.blankframework.database.TableMenuDetails;
import com.malviya.blankframework.models.DashboardCellDataHolder;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;
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
        UserInfo.parentId = "parent1";
        UserInfo.parentName = "Prafulla";
        UserInfo.studentId = "student1";
        //need to fetch data from DB WRT to above parameters
        //----------------------------------------------------------
        TableMenuDetails table = new TableMenuDetails();
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


    private void addCellItem() {
        DashboardCellDataHolder holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.noticeboard);
        holder.setText(getString(R.string.notice_board));
        holder.setNotification("33");
        holder.setColor(R.color.colorLightGreen);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.attendance);
        holder.setText(getString(R.string.attendance));
        holder.setNotification("3");
        holder.setColor(R.color.colorLightYellow);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.homework);
        holder.setNotification("1");
        holder.setText(getString(R.string.homework));
        holder.setColor(R.color.colorLightWallet);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.diary);
        holder.setNotification("1");
        holder.setText(getString(R.string.diary));
        holder.setColor(R.color.colorDarkBlue);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.messages);
        holder.setNotification("1");
        holder.setText(getString(R.string.message));
        holder.setColor(R.color.colorSkyBlue);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.events);
        holder.setNotification("1");
        holder.setText(getString(R.string.events));
        holder.setColor(R.color.colorDarkYellow);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.gallery);
        holder.setNotification("1");
        holder.setText(getString(R.string.gallery));
        holder.setColor(R.color.colorLightVallet);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.fee);
        holder.setNotification("1");
        holder.setText(getString(R.string.feedback));
        holder.setColor(R.color.colorLightRed);
        mCellList.add(holder);

        holder = new DashboardCellDataHolder();
        holder.setImage(R.drawable.fee);
        holder.setNotification("1");
        holder.setText(getString(R.string.fee));
        holder.setColor(R.color.colorLightGray);
        mCellList.add(holder);

    }


}
