package com.example.rail.Fragment2;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rail.R;

import java.util.List;
public class pinleiAdapter extends ArrayAdapter {
    private final int resourceId;

    public pinleiAdapter(Context context, int textViewResourceId, List<cat_pinlei> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cat_pinlei fruit = (cat_pinlei) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView pinleiImage = (ImageView) view.findViewById(R.id.pinlei_image);//获取该布局内的图片视图
        TextView pinleiName = (TextView) view.findViewById(R.id.pinlei_name);
        TextView pinleilife = (TextView) view.findViewById(R.id.pinlei_life);
        TextView pinleichar = (TextView) view.findViewById(R.id.pinlei_char);//获取该布局内的文本视图
        pinleiImage.setImageResource(fruit.getImageId());//为图片视图设置图片资源
        pinleiName.setText(fruit.getName());
        pinleilife.setText(fruit.getLife());
        pinleichar.setText(fruit.getChar());//为文本视图设置文本内容
        return view;
    }
}
