package com.example.rail.ui.activity;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PopTool implements ChooseAdapter.IOnItemSelectListener{

    private TextView text1;
    private TextView text2;//显示在这个TextView下面
    private RelativeLayout relativeLayout;//点击此layout显示popwindow
    private ImageView image;

    private ChoosePopwindow choosePopwindow;
    private ChooseAdapter mAdapter;

    private List<String> mListType = new ArrayList<String>();

    public PopTool(RelativeLayout relativeLayout, TextView text1, TextView text2, ImageView image, List<String> mListType, final Context context){
        this.relativeLayout =relativeLayout;
        this.image = image;
        this.mListType = mListType;
        this.text1 = text1;
        this.text2 = text2;
        init(context);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinWindow(context);
            }
        });
    }

    public void init(final Context context){
        mAdapter = new ChooseAdapter(context, mListType);
        mAdapter.refreshData(mListType, 0);
        choosePopwindow = new ChoosePopwindow(context);
        choosePopwindow.setAdapter(mAdapter);
        choosePopwindow.setItemListener(this);

    }

    //设置PopWindow
    public void showSpinWindow(Context context) {

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        int width = dm.widthPixels;
        //设置popwindow大小
        choosePopwindow.setWidth(width / 2);
        //设置显示的位置在哪个控件的下方
        choosePopwindow.showAsDropDown(text2);

    }

    @Override
    public void onItemClick(int pos) {
        //将选中的内容显示在指定控件中
        String value = mListType.get(pos);
        text2.setText(value);
    }
}
