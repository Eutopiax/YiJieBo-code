package com.example.rail.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rail.MainActivity;
import com.example.rail.MyDatabaseHelper;
import com.example.rail.db.LoginUser;
import com.example.rail.R;

import java.util.ArrayList;
import java.util.List;

public class donate extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    public static final String BTN_MODE = "BTNMODE"; //按钮模式
    public static final String TEV_MODE = "TEVMODE"; //文本模式

    private static final String TAG = "IViewGroup";
    private final int HorInterval = 10;    //水平间隔
    private final int VerInterval = 10;    //垂直间隔

    private int viewWidth;   //控件的宽度
    private int viewHeight;  //控件的高度

    private ArrayList<String> mTexts = new ArrayList<>();
    private Context mContext;
    private int textModePadding = 15;

    //正常样式
    private float itemTextSize = 18;
    private int itemBGResNor = R.drawable.goods_item_btn_normal;
    private int itemTextColorNor = Color.parseColor("#000000");

    //选中的样式
    private int itemBGResPre = R.drawable.goods_item_btn_selected;
    private int itemTextColorPre = Color.parseColor("#ffffff");

    private List<donation> donationList = new ArrayList<donation>();
    private ListView listView = null;

    private ImageView bt;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.donation,container,false);
        return view;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttongroup);
        initFruits(); // 初始化水果数据
        donationAdapter adapter = new donationAdapter(donate.this, R.layout.donation, donationList);//实例化FruitAdapter
        listView = (ListView) findViewById(R.id.listView);//绑定Listview
        listView.setAdapter(adapter);//设置Adapter
        bt = findViewById(R.id.back);
        bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
            @Override
            public void onClick(View v){
                dbHelper=new MyDatabaseHelper(donate.this);
                SQLiteDatabase sdb=dbHelper.getWritableDatabase();
                String sql="select * from donation where userid=?";
                Cursor cursor=sdb.rawQuery(sql, new String[]{Integer.toString(LoginUser.getInstance().getId())});
                int maoliang =cursor.getInt(cursor.getColumnIndex("maoliang"));
                int maobuhe =cursor.getInt(cursor.getColumnIndex("maobuhe"));
                int maosha =cursor.getInt(cursor.getColumnIndex("maosha"));
                int maoguantou =cursor.getInt(cursor.getColumnIndex("maoguantou"));
                maoliang=maoliang;
                Intent i = new Intent(donate.this, MainActivity.class); //切换窗口
                startActivity(i);
            }
        });
    }
    private void initFruits() {
        donation maoliang = new donation("您已捐赠的数量："," ","请输入你想捐赠的猫粮个数：",R.drawable.maoliang); //添加苹果图片
        donationList.add(maoliang);
        donation maosha = new donation("您已捐赠的数量："," ","请输入你想捐赠的猫砂袋数：",R.drawable.maosha); //添加苹果图片
        donationList.add(maosha);
        donation maoguantou = new donation("您已捐赠的数量："," ","请输入你想捐赠的猫罐头数：", R.drawable.maoguantou); //添加苹果图片
        donationList.add(maoguantou);
        donation maobohe = new donation("您已捐赠的数量："," ","请输入你想捐赠的猫草斤数：", R.drawable.maobohe); //添加苹果图片
        donationList.add(maobohe);

    }
}