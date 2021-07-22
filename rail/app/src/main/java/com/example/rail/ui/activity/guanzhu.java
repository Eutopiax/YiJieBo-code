package com.example.rail.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rail.Fragment1.catAdapter;
import com.example.rail.Fragment1.catinfo;
import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.LoginUser;
import com.example.rail.db.cat;
import com.example.rail.util.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class guanzhu extends AppCompatActivity {
    MyDatabaseHelper dbHelper;
    private List<cat> catList = new ArrayList<cat>();
    private ListView listView = null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_guanzhu);
        initFruits(); // 初始化水果数据
        catAdapter adapter = new catAdapter(this, R.layout.schoolcat_item, catList);//实例化FruitAdapter
        listView = findViewById(R.id.listView);//绑定Listview
        listView.setAdapter(adapter);//设置Adapter
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cat item=(cat) adapter.getItem(position);
                Intent i = new Intent(guanzhu.this, catinfo.class);
                i.putExtra("cat",item);
                startActivity(i);
            }

        });
    }

    private void initFruits() {

        String sql1="select * from focus where userid= ?";
        dbHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        Cursor cursor=sdb.rawQuery(sql1, new String[]{Integer.toString(LoginUser.getInstance().getId())});
        while(cursor.moveToNext()){
            int catid = cursor.getInt(cursor.getColumnIndex("catid"));
            Log.e("FUCK",Integer.toString(catid));
            String sql2="select * from cat where id= ?";
            Cursor cursor1=sdb.rawQuery(sql2, new String[]{Integer.toString(catid)});
            cursor1.moveToNext();
            String catname = cursor1.getString(cursor1.getColumnIndex("catname"));
            String maose = cursor1.getString(cursor1.getColumnIndex("maose"));
            String birthday = cursor1.getString(cursor1.getColumnIndex("birthdate"));
            String sex = cursor1.getString(cursor1.getColumnIndex("sex"));
            String condition = cursor1.getString(cursor1.getColumnIndex("condition"));
            String character = cursor1.getString(cursor1.getColumnIndex("character"));
            byte[] image = cursor1.getBlob(cursor1.getColumnIndex("image"));
            cat tempcat = new cat(catid,catname,maose,birthday,sex,condition,character,image);
            catList.add(tempcat);
        }
    }
}
