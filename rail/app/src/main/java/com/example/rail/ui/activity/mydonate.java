package com.example.rail.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rail.MainActivity;
import com.example.rail.R;
import com.example.rail.db.NewsBean;
import com.example.rail.util.ActivityCollector;
import com.example.rail.util.NewsUtils;

import java.util.ArrayList;

public class mydonate extends AppCompatActivity  {
    private ImageView bt;
    private ListView lv;
    private ArrayList<NewsBean> mList;
    /*
    test
     */
    /*
    test
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_mydonate);
        bt = findViewById(R.id.back);
        bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
            @Override
            public void onClick(View v){
                Intent i = new Intent(mydonate.this, MainActivity.class); //切换窗口
                startActivity(i);
            }
        });
        initUI();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        lv.setAdapter(new NewsAdapter());
    }

    private void initData() {
        mList = NewsUtils.getAllNews(this);
    }

    private void initUI() {
        lv = (ListView) findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(id == 0)
                {
                    setContentView(R.layout.olddon);
                    bt = findViewById(R.id.backk);
                    bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
                    @Override
                    public void onClick(View v){
                        Intent i = new Intent(mydonate.this, mydonate.class); //切换窗口
                        startActivity(i);
                    }
                });}
                if(id == 1)
                {
                    setContentView(R.layout.olddon1);
                    bt = findViewById(R.id.backk);
                    bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
                        @Override
                        public void onClick(View v){
                            Intent i = new Intent(mydonate.this, mydonate.class); //切换窗口
                            startActivity(i);
                        }
                    });}
                if(id == 2)
                {
                    setContentView(R.layout.olddon2);
                    bt = findViewById(R.id.backk);
                    bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
                        @Override
                        public void onClick(View v){
                            Intent i = new Intent(mydonate.this, mydonate.class); //切换窗口
                            startActivity(i);
                        }
                    });}
                if(id == 3)
                {
                    setContentView(R.layout.olddon3);
                    bt = findViewById(R.id.backk);
                    bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
                        @Override
                        public void onClick(View v){
                            Intent i = new Intent(mydonate.this, mydonate.class); //切换窗口
                            startActivity(i);
                        }
                    });}
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(mList.get(position).news_url));
//                startActivity(intent);

            }
        });
    }

    private class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public NewsBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.listview_item, null);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_des = (TextView) convertView.findViewById(R.id.tv_des);
                holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            NewsBean item = getItem(position);
            holder.tv_title.setText(item.title);
            holder.tv_des.setText(item.des);
            holder.iv_icon.setImageDrawable(item.icon);
            return convertView;
        }
    }

    private static class ViewHolder {
        TextView tv_title;
        TextView tv_des;
        ImageView iv_icon;

    }


}
