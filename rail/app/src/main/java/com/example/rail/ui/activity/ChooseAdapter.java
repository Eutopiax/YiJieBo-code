package com.example.rail.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rail.R;

import java.util.List;

public class ChooseAdapter extends BaseAdapter {

    //popwindow中listview的适配器
    private List<String> mList;
    private LayoutInflater mInflater;

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.choose_item, null);
            viewHolder = new ViewHolder();
            viewHolder.text = view.findViewById(R.id.cup_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.text.setText(mList.get(position));

        return view;
    }

    public static class ViewHolder {
        public TextView text;
    }

    public ChooseAdapter(Context context,List<String> mList){
        this.mList = mList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//  取得xml里定义的view
    }


    public void refreshData(List<String> objects, int index) {
        mList = objects;
        if (index < 0) {
            index = 0;
        }
        if (index >= mList.size()) {
            index = mList.size() - 1;
        }
    }

    //listview点击事件
    public static interface IOnItemSelectListener {
        public void onItemClick(int pos);
    }

}