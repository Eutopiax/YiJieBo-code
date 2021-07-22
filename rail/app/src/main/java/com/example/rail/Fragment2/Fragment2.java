package com.example.rail.Fragment2;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.order;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Fragment2<Fragmentmanager> extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private Fragment2_1 fragment1;
    private Fragment2_2 fragment2;
    private Fragment2_3 fragment3;
    private Fragment2_4 fragment4;
    private Fragment2_5 fragment5;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment

    List<order> ord;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);
        return view;
    }
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        initFragment(view);

    }
    //初始化fragment和fragment数组
    private void initFragment(View view)
    {

        ord=new ArrayList<order>();
        //从数据库里面把数据取出来
        MyDatabaseHelper dbHelper=new MyDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query("orderr", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String d = cursor.getString(cursor.getColumnIndex("date"));
            String s = cursor.getString(cursor.getColumnIndex("startdestination"));
            String e = cursor.getString(cursor.getColumnIndex("enddestinatioin"));
            String u = cursor.getString(cursor.getColumnIndex("usetime"));
            order o=new order(d, s, e, u);

            ord.add(o);
        }
        LinearLayout ll=(LinearLayout)view.findViewById(R.id.ll);

        //把数据显示到屏幕
        for(order o:ord)
        {
            //1.集合中每有一条数据，就new一个TextView
            RelativeLayout rl =new  RelativeLayout(getActivity());

            TextView tv=new TextView(getActivity());
            TextView tv1=new TextView(getActivity());
            TextView tv2=new TextView(getActivity());
            TextView tv3=new TextView(getActivity());
            TextView line=new TextView(getActivity());
            tv.setTextColor(Color.WHITE);
            tv1.setTextColor(Color.WHITE);
            tv2.setTextColor(Color.WHITE);
            tv3.setTextColor(Color.WHITE);
            line.setTextColor(Color.WHITE);
            line.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            //2.把人物的信息设置为文本的内容


            tv.setText("    出发时间:"+o.get_date()+"\n"+"  出发地点:"+o.get_startdestination()+
                    "\n"+"  到达地点:丰台科技园地铁站"+"\n"+"   用时:"+o.get_useTime());
            tv.setTextSize(18);
            tv1.setText("出发地点:"+o.get_startdestination());
            tv1.setTextSize(18);
            tv2.setText("到达地点:丰台科技园地铁站");
            tv2.setTextSize(18);
            tv3.setText("用时:"+o.get_useTime()+"分钟");
            tv3.setTextSize(18);
            //3.把TextView设置成线性布局的子节点
            rl.addView(tv);
//            rl.addView(tv1);
//            rl.addView(tv2);
//            rl.addView(tv3);

            rl.setBackgroundResource(R.drawable.shape_corner2);

            ll.addView(rl);


        }

    }

    //判断选择的菜单
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
    //切换Fragment
    private void switchFragment(int lastfragment,int index)
    {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if(fragments[index].isAdded()==false)
        {
            transaction.add(R.id.mainview,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }

}


