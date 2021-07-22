package com.example.rail.ui.activity;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.rail.R;

import java.util.List;

public class ChoosePopwindow extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private ChooseAdapter mAdapter;
    private ChooseAdapter.IOnItemSelectListener mItemSelectListener;

    public ChoosePopwindow(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.choose_window, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.spinner_lv);
        mListView.setOnItemClickListener(this);
    }

    public void refreshData(List<String> list, int selIndex) {
        if (list != null && selIndex != -1) {
            if (mAdapter != null) {
                mAdapter.refreshData(list, selIndex);
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onItemClick(position);
        }
    }

    public void setItemListener(ChooseAdapter.IOnItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    public void setAdapter(ChooseAdapter adapter) {
        mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }
}