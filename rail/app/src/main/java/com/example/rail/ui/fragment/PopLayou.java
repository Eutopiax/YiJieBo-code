package com.example.rail.ui.fragment;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rail.R;

public class PopLayou extends  LinearLayout{
    //自定义控件
    private TextView popText;
    private TextView popChoose;
    private ImageView popImg;
    private RelativeLayout popRl;

    //重写LinearLayout带有两个参数的构造函数
    public PopLayou(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.pop_view, this);
        popText = findViewById(R.id.pop_text);
        popChoose = findViewById(R.id.pop_choose);
        popImg = findViewById(R.id.pop_img);
        popRl = findViewById(R.id.pop_rl);
    }


    public TextView getPopText() {
        return popText;
    }

    public TextView getPopChoose() {
        return popChoose;
    }

    public ImageView getPopImg() {
        return popImg;
    }

    public RelativeLayout getPopRl() {
        return popRl;
    }

}
