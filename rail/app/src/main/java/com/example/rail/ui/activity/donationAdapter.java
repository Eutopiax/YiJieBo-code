package com.example.rail.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rail.R;

import java.util.List;
public class donationAdapter extends ArrayAdapter {
    private final int resourceId;

    public donationAdapter(Context context, int textViewResourceId, List<donation> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        donation fruit = (donation) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView donateImage = (ImageView) view.findViewById(R.id.donate_image);//获取该布局内的图片视图
        TextView donateName = (TextView) view.findViewById(R.id.donate_name);
        TextView donateSen = (TextView) view.findViewById(R.id.donate_sen);
        TextView donatecount = (TextView) view.findViewById(R.id.donate_count);
//获取该布局内的文本视图
        donateImage.setImageResource(fruit.getImageId());//为图片视图设置图片资源
        donateName.setText(fruit.getName());
        donateSen.setText(fruit.getSentence());
        donatecount.setText(fruit.getCount());
        //为文本视图设置文本内容
        return view;
    }
}
