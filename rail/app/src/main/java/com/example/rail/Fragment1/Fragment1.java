package com.example.rail.Fragment1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextPaint;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TimePicker;

import android.widget.TextView;
import android.os.Handler;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.LoginUser;
import com.example.rail.db.order;
import com.example.rail.ui.activity.PopTool;
import com.example.rail.ui.activity.Register;
import com.example.rail.ui.activity.UserService;
import com.example.rail.ui.activity.location;
import com.example.rail.ui.fragment.PopLayou;
import com.example.rail.ui.fragment.PopLayout;
import com.example.rail.ui.locserv.Send;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static java.lang.Thread.sleep;


public class Fragment1 extends Fragment implements OnSeekBarChangeListener,View.OnClickListener {
    private LoginUser loginUser = LoginUser.getInstance();
    private BottomNavigationView bottomNavigationView;
    private PopLayou mPopLayout;
    private PopLayou mPopLayout2;
    private PopLayou mPopLayout3;
    private PopLayou mPopLayout4;
    private PopLayou mPopLayout5;
    private PopLayout mPopLayout6;
    String RequestFromUser;
    int flag=-1;
    TimePickerView pvTime;
    private PopTool mPopTool;
    private PopTool mPopTool1;
    private PopTool mPopTool2;
    private PopTool mPopTool3;
    private PopTool mPopTool4;
    private PopTool mPopTool5;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private TextView text9;
    private TextView text10;

    private TextView et;
    private TextView st;
    private TextView ett;
    private ImageView image;
    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout5;

    private List<String> mList = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private List<String> mList3 = new ArrayList<>();
    private List<String> mList4 = new ArrayList<>();
    private List<String> mList5 = new ArrayList<>();
    private List<String> mList6 = new ArrayList<>();
    private Button fasong;
    private final int HANDLER_MSG_TELL_RECV = 0x124;
    private Fragment[] fragments;
    private int lastfragment;//???????????????????????????Fragment

    private AMap aMap;
    private MapView mapView;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;// ????????????
    private boolean isFirst = true;
    private static double lat,lng;//????????????????????????
    private Button start;//??????????????????
    private Button setlayou;
    private Button setlayou1;

    private double latitude,longitude;//??????????????????????????????
    private List<Marker> list;//?????????????????????list
    private Marker marker,markerOwner;//?????????marker,???????????????marker
    private boolean isClick = false;//???????????????????????????????????????,?????????????????????????????????
    RelativeLayout setlayout;
    RelativeLayout setlayout1;
    RelativeLayout setlayout3;
    private SeekBar mseekbar;
    private SeekBar mseekbar2;
    private SeekBar mseekbar3;
    private TextView mTvDef;
    private TextView mTvDef2;
    private TextView mTvDef3;
    private TextView mTvDef4;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    double price=1;
    private SmoothMoveMarker moveMarker;
    int count = 0;

    private static final int START_STATUS=0;

    private static final int MOVE_STATUS=1;

    private static final int PAUSE_STATUS=2;
    private static final int FINISH_STATUS=3;
    double a1 = 0,a2=0,a3=0;
    private int mMarkerStatus=START_STATUS;
    Polyline line;
    String str="";
    String[] split1;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }

    private void initList(View view) {
        mList.add("??????????????????");
        mList.add("SEC??????");
        mList.add("?????????????????????????????????");
        mList.add("??????????????????");
        mList.add("????????????");
        mList.add("?????????");
        mList.add("??????????????????");
        mList.add("????????????????????????");
        mList.add("??????????????????");
        mList.add("??????????????????");
        mList.add("????????????");
        mList.add("?????????????????????");
        mList.add("????????????");
        mList.add("????????????");
        mList.add("??????????????????");
        mList.add("?????????????????????");
        mList2.add("1");
        mList2.add("2");
        mList2.add("3");
        mList2.add("4");
        mList2.add("5");
        mList2.add("6");
        mList2.add("7");
        mList2.add("8");
        mList2.add("9");
        mList2.add("10");
        mList2.add("11");

//        mList3.add("1");
//        mList3.add("2");
//        mList3.add("3");
//        mList3.add("4");
//        mList3.add("5");
//        mList3.add("6");
//        mList3.add("7");
//        mList3.add("8");
//        mList3.add("9");
//        mList3.add("10");
//
//        mList4.add("1");
//        mList4.add("2");
//        mList4.add("3");
//        mList4.add("4");
//        mList4.add("5");
//        mList4.add("6");
//        mList4.add("7");
//        mList4.add("8");
//        mList4.add("9");
//        mList4.add("10");
//
//        mList5.add("1");
//        mList5.add("2");
//        mList5.add("3");
//        mList5.add("4");
//        mList5.add("5");



    }

    private void init(View view){
        mPopLayout = view.findViewById(R.id.pop_layout);
        mPopLayout2 = view.findViewById(R.id.pop_layout2);
//        mPopLayout3 = view.findViewById(R.id.pop_layout3);
//        mPopLayout4 = view.findViewById(R.id.pop_layout4);
//        mPopLayout5 = view.findViewById(R.id.pop_layout5);


        text1 = mPopLayout.getPopText();
        text2 = mPopLayout.getPopChoose();
        text3 = mPopLayout2.getPopText();
        text4 = mPopLayout2.getPopChoose();
//        text5 = mPopLayout3.getPopText();
//        text6 = mPopLayout3.getPopChoose();
//        text7 = mPopLayout4.getPopText();
//        text8 = mPopLayout4.getPopChoose();
//        text9 = mPopLayout5.getPopText();
//        text10 = mPopLayout5.getPopChoose();


        image = mPopLayout.getPopImg();
        relativeLayout = mPopLayout.getPopRl();
        relativeLayout1 = mPopLayout2.getPopRl();
//        relativeLayout2 = mPopLayout3.getPopRl();
//        relativeLayout3 = mPopLayout4.getPopRl();
//        relativeLayout4 = mPopLayout5.getPopRl();

        mPopTool = new PopTool(relativeLayout,text1,text2,image,mList,getActivity());
        mPopTool1 = new PopTool(relativeLayout1,text3,text4,image,mList2,getActivity());
//        mPopTool2 = new PopTool(relativeLayout2,text5,text6,image,mList3,getActivity());
//        mPopTool3 = new PopTool(relativeLayout3,text7,text8,image,mList4,getActivity());
//        mPopTool4 = new PopTool(relativeLayout4,text9,text10,image,mList5,getActivity());

    }
    private Spinner spType;
//    ?????????????????????
    private void newMap(double l1,double l2) {

//        mAMap = mapView.getMap();
//        latitude=39.825233;
//        longitude=116.297176;
//        latitude=l1;
//        longitude=l2;
        CameraPosition cameraPosition = new CameraPosition(new LatLng(l1, l2), 15, 0, 30);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.moveCamera(cameraUpdate);
        drawMarkers(l1,l2);
    }
    //??????????????????
    public void drawMarkers(double l1,double l2) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(l1, l2))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.dingwei))
                .draggable(true);
        marker = aMap.addMarker(markerOptions);

        marker.showInfoWindow();
    }
    private void initView(){
        list = new ArrayList<>();
    }
    private void initMap(){
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        setUpMap();
    }
    /**
     * ??????????????????
     */
    private void setUpMap(){
//        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);// ?????????true??????????????????????????????????????????false??????????????????????????????????????????????????????false
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);// ????????????
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// ????????????????????????????????????
        aMap.setMyLocationEnabled(true);// ?????????true??????????????????????????????????????????false??????????????????????????????????????????????????????false
    }
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        initList(view);
        init(view);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// ?????????????????????

//        latitude=39.909187;
//        longitude=116.397451;

        initMap();
        initView();

        setlayout = (RelativeLayout)view.findViewById(R.id.setlayout);
        setlayout1 = (RelativeLayout)view.findViewById(R.id.setlayout1);
        setlayout3 = (RelativeLayout)view.findViewById(R.id.setlayout3);
        setlayout3.setVisibility(View.GONE);
        setlayout.setVisibility(View.GONE);

        setlayou = (Button)view.findViewById(R.id.setgeren);
        setlayou1 = (Button)view.findViewById(R.id.setback);

        mTvDef = (TextView)view.findViewById(R.id.tv_def);
        mTvDef2 = (TextView)view.findViewById(R.id.tv_def2);
        mTvDef3 = (TextView)view.findViewById(R.id.tv_def3);
        mTvDef4 = (TextView)view.findViewById(R.id.tv_def4);
        mTvDef.setText("0.3");
        mTvDef2.setText("0.3");
        mTvDef3.setText("0.3");
        mTvDef4.setText("?????????:"+String.valueOf(price)+"???");
        tv1 = (TextView)view.findViewById(R.id.back1);
        tv2 = (TextView)view.findViewById(R.id.back2);
        tv3 = (TextView)view.findViewById(R.id.back3);
        tv4 = (TextView)view.findViewById(R.id.back4);


        mseekbar=(SeekBar)view.findViewById(R.id.seekbar);
        mseekbar2=(SeekBar)view.findViewById(R.id.seekbar2);
        mseekbar3=(SeekBar)view.findViewById(R.id.seekbar3);

        mseekbar.setOnSeekBarChangeListener(this);
        mseekbar2.setOnSeekBarChangeListener(this);
        mseekbar3.setOnSeekBarChangeListener(this);


        et=(TextView) view.findViewById(R.id.destination);
        st=(TextView) view.findViewById(R.id.starttime);
        ett=(TextView) view.findViewById(R.id.endtime);
        spType = view.findViewById(R.id.sp_type);
        setlayou.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setlayout.setVisibility(View.VISIBLE);
                setlayout1.setVisibility(View.GONE);
            }
        });
        setlayou1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setlayout.setVisibility(View.GONE);
                setlayout1.setVisibility(View.VISIBLE);
            }
        });
        //	Splinner???????????????
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String info = (String) spType.getSelectedItem();
                et.setText(info);
//                latitude=39.825233;
////        longitude=116.297176;
                newMap(39.825233,116.297176);
//                initAMap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        text1.setText("???????????????");
        text3.setText("???????????????");
//        text5.setText("???????????????????????????");
//        text7.setText("???????????????????????????????????????");
//        text9.setText("?????????????????????????????????????????????");
        fasong=view.findViewById(R.id.fasong);
        Button timebtn = (Button)view.findViewById(R.id.xuanzeshijian);
        timebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        RequestFromUser+="_"+hourOfDay+":"+minute+"_";
                        System.out.println("???????????????????????????"+hourOfDay+minute);
                        st.setText("??????????????????"+hourOfDay+":"+minute);
                       // Toast.makeText(Fragment1.this, "???????????????????????????"+hourOfDay+minute, Toast.LENGTH_SHORT).show();
                    }
                },00,00,true).show();
            }
        });
        Button timebtn1 = (Button)view.findViewById(R.id.xuanzeshijian2);
        timebtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        RequestFromUser+="_"+hourOfDay+":"+minute+"_";
                        System.out.println("???????????????????????????"+hourOfDay+minute);
                        ett.setText("??????????????????"+hourOfDay+":"+minute);
                        // Toast.makeText(Fragment1.this, "???????????????????????????"+hourOfDay+minute, Toast.LENGTH_SHORT).show();
                    }
                },00,00,true).show();
            }
        });
        fasong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch((String)text2.getText()){
                    case "??????????????????" :
                        flag=2;
                        break;
                    case "SEC??????" :
                        flag=3;
                        break;
                    case "?????????????????????????????????" :
                        flag=4;
                        break;
                    case "??????????????????" :
                        flag=5;
                        break;
                    case "????????????" :
                        flag=6;
                        break;
                    case "?????????" :
                        flag=7;
                        break;
                    case "??????????????????" :
                        flag=8;
                        break;
                    case "????????????????????????" :
                        flag=9;
                        break;
                    case "??????????????????" :
                        flag=10;
                        break;
                    case "??????????????????" :
                        flag=11;
                        break;
                    case "????????????" :
                        flag=12;
                        break;
                    case "?????????????????????" :
                        flag=13;
                        break;
                    case "??????????????????" :
                        flag=14;
                        break;
                    case "??????????????????" :
                        flag=15;
                        break;
                    case "????????????" :
                        flag=16;
                        break;
                    case "????????????" :
                        flag=17;
                        break;
                    case "??????????????????" :
                        flag=18;
                        break;
                    case "?????????????????????" :
                        flag=19;
                        break;
                    default :
                }
                double iniTimeF,iniTimeO,iniComfort;
//                iniComfort=mTvDef3.getText();
//                iniTimeF=Integer.parseInt((String)text8.getText());
//                iniTimeO=1/Double.parseDouble((String)text10.getText());
//

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy???MM???dd??? HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());

                String [] temp = null;
                String [] temp2 =null;
                temp = simpleDateFormat.format(date).split(" ");
                temp2=temp[1].split(":");
                RequestFromUser+=temp2[0]+":"+temp2[1];
                RequestFromUser+="_"+(flag);
                RequestFromUser+="_"+ mTvDef3.getText();
                RequestFromUser+="_"+ mTvDef.getText();
                RequestFromUser+="_"+ mTvDef2.getText();
                RequestFromUser+="_"+(String)text4.getText();
                RequestFromUser+="_"+loginUser.getName();
                System.out.println(RequestFromUser);
                startNetThread("192.168.0.106",21567,RequestFromUser);
                RequestFromUser="";

                while (str==""){
                }
//

                split1 = str.split("_");
                TextPaint tp = tv1.getPaint();
                tp.setFakeBoldText(true);

                String[] route2 = split1[2].split("-");


//
                if(flag==2){
                    coords = new double[]{116.297176, 39.825233,
                            116.297177, 39.825169,
                            116.295219, 39.825098,
                    };
                    coords1 = new double[]{116.295219, 39.825098,
                            116.297177, 39.825169,
                            116.297176, 39.825233,
                    };
                };
                if(flag==17){
                    coords = new double[]{116.297255,39.825187,
                            116.29972,39.825252,
                            116.300431,39.82523,
                            116.300439,39.82716,
                            116.30045,39.827522,
                            116.30043,39.828277
                    };
                    coords1 = new double[]{116.30043,39.828277,
                            116.300434,39.829694,
                            116.300439,39.830225,
                            116.297392,39.830019,
                            116.297254,39.825308
                    };
                };
//                if(flag==3){
//                    coords = new double[]{116.297176, 39.825233,
//                            116.293513,39.825051,
//                            116.295219, 39.825098,
//                    };
//                };
//                if(flag==4){
//
//                };
//                if(flag==5){
//
//                };
//                if(flag==6){
//
//                };if(flag==7){
//
//                };if(flag==8){
//
//                };if(flag==9){
//
//                };if(flag==10){
//
//                };
//                if(flag==11){
//
//                };
//                if(flag==12){
//
//                };
//                if(flag==13){
//
//                };
//                if(flag==14){
//
//                };
//                if(flag==15){
//
//                };
//                if(flag==16){
//
//                };
//                if(flag==17){
//
//                };
//                if(flag==18){
//
//                };
//                if(flag==19){
//
//                };
//




                initMoveMarker(coords);
                moveMarker.startSmoothMove();


                setlayout1.setVisibility(View.GONE);
//                final TranslateAnimation ctrlAnimation = new TranslateAnimation(0, 100,0, 0);
                final TranslateAnimation ctrlAnimation = new TranslateAnimation(
                        TranslateAnimation.RELATIVE_TO_SELF, 1, TranslateAnimation.RELATIVE_TO_SELF, 0,
                        TranslateAnimation.RELATIVE_TO_SELF, 0, TranslateAnimation.RELATIVE_TO_SELF, 0);

                ctrlAnimation.setDuration(600l);     //???????????????????????????
                setlayout3.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        setlayout3.setVisibility(View.VISIBLE);
                        setlayout3.startAnimation(ctrlAnimation);
                    }
                }, 300);



                tv1.setText(split1[3]+"??? ??????????????????");
                tv3.setText("??????"+split1[0]+"????????????????????????");
                tv4.setText("???????????????"+split1[1]+"????????????????????????");
                long timeMillis = Calendar.getInstance().getTimeInMillis();
                SimpleDateFormat sdfTwo =new SimpleDateFormat("yyyy???MM???dd???HH???mm???ss???", Locale.getDefault());
                String time44 = sdfTwo.format(timeMillis);
//                while(flag!=20);
//                initMoveMarker(view,savedInstanceState,coords1);
//                moveMarker.startSmoothMove();
                MyDatabaseHelper dbHelper=new MyDatabaseHelper(getActivity());
                SQLiteDatabase sdb=dbHelper.getWritableDatabase();
                String sql1="create table if not exists orderr(id integer primary key autoincrement,date varchar(20),startdestination varchar(20),enddestinatioin varchar(20),usetime varchar(20))";
                sdb.execSQL(sql1);

                order or = new order(time44,(String)text2.getText(),"????????????????????????",split1[1]);
                UserService uService=new UserService(getActivity());
                uService.setorder(or);

            }
        });

    }

    private void startNetThread(final String host, final int port, final String data) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(host, port);
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write((data).getBytes());
                    outputStream.flush();
                    System.out.println(socket);

                    InputStream is = socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int n = is.read(bytes);

                    str=new String(bytes, 0, n);
                    System.out.println("?????????????????????"+str);
//                    Message msg = handler.obtainMessage(HANDLER_MSG_TELL_RECV, new String(bytes, 0, n));
//                    msg.sendToTarget();
                    while(flag!=20);
                    System.out.println("test");

                    initMoveMarker(coords1);

                    moveMarker.startSmoothMove();



                    is.close();
                    socket.close();
                } catch (Exception e) {
                }

            }
        };

        thread.start();
    }

//    Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage((String)msg.obj);
//
//            builder.create().show();
//        };
//    };

    //?????????????????????
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment= new BottomNavigationView.OnNavigationItemSelectedListener() {
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.id1:
                {
                    if(lastfragment!=0)
                    {
                        switchFragment(lastfragment,0);
                        lastfragment=0;

                    }

                    return true;
                }
                case R.id.id2:
                {
                    if(lastfragment!=1)
                    {
                        switchFragment(lastfragment,1);
                        lastfragment=1;

                    }

                    return true;
                }
                case R.id.id3:
                {
                    if(lastfragment!=2)
                    {
                        switchFragment(lastfragment,2);
                        lastfragment=2;

                    }


                    return true;
                }
                case R.id.id4:
                {
                    if(lastfragment!=3)
                    {
                        switchFragment(lastfragment,3);
                        lastfragment=3;

                    }

                    return true;
                }
                case R.id.id5:
                {
                    if(lastfragment!=4)
                    {
                        switchFragment(lastfragment,4);
                        lastfragment=4;

                    }

                    return true;
                }
            }


            return false;
        }
    };
    //??????Fragment
    private void switchFragment(int lastfragment,int index)
    {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//????????????Fragment
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.mainview,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        Log.d(TAG, "seekid:"+seekBar.getId()+", progess"+progress);

        switch(seekBar.getId()) {
            case R.id.seekbar:{
                // ????????????????????????SeekBar?????????TextView?????????
                double num=(double)seekBar.getProgress()/100;
                a1=num;
                mTvDef.setText(String.valueOf(num));

                break;
            }
            case R.id.seekbar2:{
                // ????????????????????????SeekBar?????????TextView?????????
                double num2=(double)seekBar.getProgress()/100;
                a2=num2;
                mTvDef2.setText(String.valueOf(num2));

                price = 1;
                break;
            }
            case R.id.seekbar3:{
                // ????????????????????????SeekBar?????????TextView?????????
                double num3=(double)seekBar.getProgress()/100;
                a3=num3;
                mTvDef3.setText(String.valueOf(num3));

                break;
            }
            default:
                break;
        }
        price=1+a1+a2+a3;
        mTvDef4.setText("?????????:"+String.valueOf(price)+"???");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

//????????????
private void initMoveMarker(double[] coords ){
//        mapView.onDestroy();
//    mapView = (MapView) view.findViewById(R.id.map);
//    mapView.onCreate(savedInstanceState);// ?????????????????????
//    aMap = mapView.getMap();

    addPolylineInPlayGround(coords);
    // ?????????????????????
    List<LatLng> points = readLatLngs(coords);
    LatLngBounds.Builder b = LatLngBounds.builder();
    for (int i = 0 ; i < points.size(); i++) {
        b.include(points.get(i));
    }
    LatLngBounds bounds = b.build();
    aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

    moveMarker = new SmoothMoveMarker(aMap);
    // ?????????????????????

    moveMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.drawable.car));

        /*
        //?????????Marker????????????????????????????????????????????????????????????????????????????????????????????????
        // LatLng drivePoint = points.get(0);//?????????????????????????????????????????????????????????????????????????????????
        LatLng drivePoint = new LatLng(39.980521,116.351905);//?????????????????????????????????????????????
        Pair<Integer, LatLng> pair = PointsUtil.calShortestDistancePoint(points, drivePoint);
        points.set(pair.first, drivePoint);
        List<LatLng> subList = points.subList(pair.first, points.size());
        // ??????????????????????????????
        smoothMarker.setPoints(subList);*/

    moveMarker.setPoints(points);//???????????????????????????list
    moveMarker.setTotalDuration(40);//??????????????????????????????

//        aMap.setInfoWindowAdapter(infoWindowAdapter);
    moveMarker.setMoveListener(
            new SmoothMoveMarker.MoveListener() {
                @Override
                public void move(final double distance) {

                    Log.i("MY","distance:  "+distance);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (infoWindowLayout != null && title != null && moveMarker.getMarker().isInfoWindowShown()) {
                                title.setText("????????????????????? " + (int) distance + "???");
                            }
                            if(distance == 0){
                                moveMarker.getMarker().hideInfoWindow();
                                mMarkerStatus=FINISH_STATUS;
//                                mStartButton.setText("??????");
                                try {
                                    sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                flag=20;
                                System.out.println("test");

                                moveMarker.destroy();
                                split1 = str.split("_");
                                tv1.setText(split1[3]+"????????????????????? ????????????????????????");
                                tv2.setText("????????????,???????????????");
                                tv3.setText("??????"+split1[1]+"????????????????????????");
                                tv4.setText("??????"+split1[1]+"????????????????????????");
                                count++;
                                if(count==2){

                                    count=0;
                                    setlayout3.setVisibility(View.GONE);
                                    setlayout1.invalidate();
                                    setlayout1.setVisibility(View.VISIBLE);

                                    aMap.clear();
                                    initMap();
                                    initView();
                                }
                            }
                        }
                    });
                }
            });
//    moveMarker.getMarker().showInfoWindow();
}


    public void move(View view) {

        moveMarker.startSmoothMove();
    }

//    AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter() {
//        @Override
//        public View getInfoWindow(Marker marker) {
//
//            return getInfoWindowView(marker);
//        }
//
//        @Override
//        public View getInfoContents(Marker marker) {
//
//
//            return getInfoWindowView(marker);
//        }
//    };

    LinearLayout infoWindowLayout;
    TextView title;
    TextView snippet;

//    private View getInfoWindowView(Marker marker) {
//        if (infoWindowLayout == null) {
//            infoWindowLayout = new LinearLayout(MainActivity.this);
//            infoWindowLayout.setOrientation(LinearLayout.VERTICAL);
//            title = new TextView(MainActivity.this);
//            snippet = new TextView(MainActivity.this);
//            title.setTextColor(Color.BLACK);
//            snippet.setTextColor(Color.BLACK);
//            infoWindowLayout.setBackgroundResource(R.drawable.infowindow_bg);
//
//            infoWindowLayout.addView(title);
//            infoWindowLayout.addView(snippet);
//        }
//
//        return infoWindowLayout;
//    }

    private void addPolylineInPlayGround(double[] coords) {
        List<LatLng> list = readLatLngs(coords);
        List<Integer> colorList = new ArrayList<Integer>();

        line = aMap.addPolyline(new PolylineOptions().setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.custtexture)) //setCustomTextureList(bitmapDescriptors)
                .addAll(list)
                .useGradient(true)
                .width(18));
    }

    private List<LatLng> readLatLngs(double[] coords) {
        List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < coords.length; i += 2) {
            points.add(new LatLng(coords[i + 1], coords[i]));
        }
        return points;
    }
    double[] coords1 = {
            116.297176, 39.825233
    };
        /*????????????????????????????????????(0-1)*/
    private double[] coords = {116.297176,39.825233,
            116.297177,39.825169,
//            116.297057,39.825185,
//            116.296284,39.825135,
//            116.295981,39.825124,
//            116.295219,39.825114,
//            116.295219,39.825108,
//            116.295219,39.825106,
//            116.295219,39.825104,
//            116.295219,39.825102,
//            116.295219,39.825100,
            116.295219,39.825098,
    };

    @Override
    public void onClick(View v) {

    }


//
}