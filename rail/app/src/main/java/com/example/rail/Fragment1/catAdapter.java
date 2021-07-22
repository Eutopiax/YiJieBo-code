package com.example.rail.Fragment1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rail.R;
import com.example.rail.db.cat;

import java.util.List;
public class catAdapter extends ArrayAdapter {
    private final int resourceId;

    public catAdapter(Context context, int textViewResourceId, List<cat> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cat fruit = (cat) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        ImageView catImage = (ImageView) view.findViewById(R.id.cat_image);//获取该布局内的图片视图
        TextView cat_id = (TextView) view.findViewById(R.id.cat_id);
        TextView catName = (TextView) view.findViewById(R.id.cat_name);
//获取该布局内的文本视图
        Bitmap imagebitmap = BitmapFactory.decodeByteArray(fruit.getImage(), 0, fruit.getImage().length);
        //将位图显示为图片
        catImage.setImageBitmap(imagebitmap);
//        catImage.setImageResource(fruit.getImageId());//为图片视图设置图片资源
        cat_id.setText(Integer.toString(fruit.getId()));
        catName.setText(fruit.getCatname());

        //为文本视图设置文本内容
        return view;
    }

}

