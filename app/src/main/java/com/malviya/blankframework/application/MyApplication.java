package com.malviya.blankframework.application;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.malviya.blankframework.database.TableMenuDetails;
import com.malviya.blankframework.database.TableMenuMaster;
import com.malviya.blankframework.database.TableParentMaster;
import com.malviya.blankframework.database.TableParentStudentRelation;
import com.malviya.blankframework.database.TableStudentDetails;
import com.malviya.blankframework.database.TableUniversityMaster;
import com.malviya.blankframework.models.TableMenuDetailsDataModel;
import com.malviya.blankframework.models.TableMenuMasterDataModel;
import com.malviya.blankframework.models.TableParentMasterDataModel;
import com.malviya.blankframework.models.TableParentStudentRelationDataModel;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.models.TableUniversityMasterDataModel;
import com.malviya.blankframework.utils.ConnectivityReceiver;

import java.util.ArrayList;


/**
 * Created by Admin on 15-11-2016.
 * This class treats as singleton. This class also handle client server communication.
 */
public class MyApplication extends Application {
    static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initVolley();
    }

    private void initVolley() {
        initMenuMasterArrayList();
        initParentMaster();
        initStudentMaster();
        initMenuDetails();
        initUnivercityMaster();
        initParentStudent();
        createDatabase();
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        //set default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.mConnectivityReceiverListener = listener;
    }

    //for testing feed data into database++++++++++++++++++++++++++++++++++++++
    private ArrayList<TableMenuMasterDataModel> tableMenuMasterList = new ArrayList<>();
    private ArrayList<TableParentMasterDataModel> tableParentMasterList = new ArrayList<>();
    private ArrayList<TableStudentDetailsDataModel> tableStudentList = new ArrayList<>();
    private ArrayList<TableUniversityMasterDataModel> tableUniList = new ArrayList<>();
    private ArrayList<TableMenuDetailsDataModel> tableMenuDetailList = new ArrayList<TableMenuDetailsDataModel>();
    private ArrayList<TableParentStudentRelationDataModel> tableParentStudentList = new ArrayList<TableParentStudentRelationDataModel>();

    private void initParentMaster() {
        TableParentMasterDataModel holder = new TableParentMasterDataModel();
        holder.setEmailid("malviya.software@gmail.com");
        holder.setImageurl("");
        holder.setParent_name("Prafulla");
        holder.setParentid("parent1");
        holder.setPhone_number("8904188389");
        tableParentMasterList.add(holder);
        holder = new TableParentMasterDataModel();
        holder.setEmailid("mridul.malviya@gmail.com");
        holder.setImageurl("");
        holder.setParent_name("Mridul");
        holder.setParentid("parent2");
        holder.setPhone_number("9885647589");
        tableParentMasterList.add(holder);
    }

    private void initParentStudent() {
        TableParentStudentRelationDataModel holder = new TableParentStudentRelationDataModel();
        holder.setIsDefault("student1");
        holder.setParentId("parent1");
        holder.setStudentid("student1");
        tableParentStudentList.add(holder);
        holder = new TableParentStudentRelationDataModel();
        holder.setIsDefault("student1");
        holder.setParentId("parent1");
        holder.setStudentid("student3");
        tableParentStudentList.add(holder);

        holder = new TableParentStudentRelationDataModel();
        holder.setIsDefault("student2");
        holder.setParentId("parent2");
        holder.setStudentid("student2");
        tableParentStudentList.add(holder);
    }


    private void initUnivercityMaster() {
        TableUniversityMasterDataModel holder = new TableUniversityMasterDataModel();
        holder.setUniversity_id("univercity1");
        holder.setUniversity_name("RTM");
        holder.setUniversity_url("http://results.rtmnuresults.org/");
        tableUniList.add(holder);
        holder = new TableUniversityMasterDataModel();
        holder.setUniversity_id("univercity2");
        holder.setUniversity_name("swami vivekanand university");
        holder.setUniversity_url("https://www.svnuniversity.co.in/mainsite/home.aspx");
        tableUniList.add(holder);
    }


    private void initMenuDetails() {
        TableMenuDetailsDataModel holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("33");
        holder.setMenuCode("MENU1");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);

        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("3");
        holder.setMenuCode("MENU2");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);

        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("23");
        holder.setMenuCode("MENU3");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);

        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("98");
        holder.setMenuCode("MENU4");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);


        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("11");
        holder.setMenuCode("MENU5");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);


        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("55");
        holder.setMenuCode("MENU6");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);


        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("1");
        holder.setMenuCode("MENU7");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);


        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("9");
        holder.setMenuCode("MENU8");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);


        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student1");
        holder.setAlert_count("243");
        holder.setMenuCode("MENU9");
        holder.setParentId("parent1");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);

        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student2");
        holder.setAlert_count("2");
        holder.setMenuCode("MENU1");
        holder.setParentId("parent2");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);

        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student2");
        holder.setAlert_count("3");
        holder.setMenuCode("MENU2");
        holder.setParentId("parent2");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);


        holder = new TableMenuDetailsDataModel();
        holder.setStudentId("student2");
        holder.setAlert_count("34");
        holder.setMenuCode("MENU3");
        holder.setParentId("parent2");
        holder.setDate_updated("16072017");
        tableMenuDetailList.add(holder);

    }

    private void initStudentMaster() {
        TableStudentDetailsDataModel holder = new TableStudentDetailsDataModel();
        holder.setCourse("math");
        holder.setImageurl("http://whatsappdp.net/wp-content/uploads/2016/03/funny-profile-pictures.jpg");
        holder.setGender("male");
        holder.setStudentId("student1");
        holder.setStudent_name("David");
        holder.setUniversity_id("univercity1");
        tableStudentList.add(holder);

        holder = new TableStudentDetailsDataModel();
        holder.setCourse("english");
        holder.setImageurl("https://cdn2.iconfinder.com/data/icons/professions/512/student_graduate_boy_profile-512.png");
        holder.setGender("female");
        holder.setStudentId("student3");
        holder.setStudent_name("Juli");
        holder.setUniversity_id("univercity1");
        tableStudentList.add(holder);

        holder = new TableStudentDetailsDataModel();
        holder.setCourse("science");
        holder.setImageurl("https://cdn2.iconfinder.com/data/icons/professions/512/student_graduate_boy_profile-512.png");
        holder.setGender("female");
        holder.setStudentId("student2");
        holder.setStudent_name("Shyli");
        holder.setUniversity_id("univercity2");
        tableStudentList.add(holder);
    }


    private void initMenuMasterArrayList() {
        TableMenuMasterDataModel holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU1");
        holder.setMenu_description("Notice Board");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU2");
        holder.setMenu_description("Attendance");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU3");
        holder.setMenu_description("Homework");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU4");
        holder.setMenu_description("Diary");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU5");
        holder.setMenu_description("Messages");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU6");
        holder.setMenu_description("Events");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU7");
        holder.setMenu_description("Gallery");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU8");
        holder.setMenu_description("Feedback");
        tableMenuMasterList.add(holder);
        holder = new TableMenuMasterDataModel();
        holder.setMenucode("MENU9");
        holder.setMenu_description("Fee");
        tableMenuMasterList.add(holder);
    }


    private void createDatabase() {


        TableMenuMaster tableMenuMaster = new TableMenuMaster();
        tableMenuMaster.openDB(getApplicationContext());
        tableMenuMaster.insert(tableMenuMasterList);
        tableMenuMaster.closeDB();
        //--------------------------------
        TableUniversityMaster table3 = new TableUniversityMaster();
        table3.openDB(getApplicationContext());
        table3.insert(tableUniList);
        table3.closeDB();
        //--------------------------------
        TableParentMaster table1 = new TableParentMaster();
        table1.openDB(getApplicationContext());
        table1.insert(tableParentMasterList);
        table1.closeDB();
        //--------------------------------
        TableStudentDetails table2 = new TableStudentDetails();
        table2.openDB(getApplicationContext());
        table2.insert(tableStudentList);
        table2.closeDB();

        //--------------------------------
        TableMenuDetails table4 = new TableMenuDetails();
        table4.openDB(getApplicationContext());
        table4.insert(tableMenuDetailList);
        table4.closeDB();
        //--------------------------------
        TableParentStudentRelation table5 = new TableParentStudentRelation();
        table5.openDB(getApplicationContext());
        table5.insert(tableParentStudentList);
        table5.closeDB();


    }
    //for testing feed data into database++++++++++++++++++++++++++++++++++++++


}