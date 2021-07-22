//package com.example.cat_caring.ui.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.cat_caring.MainActivity;
//import com.example.cat_caring.R;
//
//import java.util.ArrayList;
//
//public class newdonate extends Activity implements donate.OnGroupItemClickListener, View.OnClickListener {
//    private donate<TextView> mGroup;
//    private Button mSubmitBtn;
//    private ArrayList<String> viewtexts = new ArrayList<>();
//    private ArrayList<String> texts = new ArrayList<>();
//
//    private int chooseID = -1;
//    private String chooseText;
//    private ImageView bt;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_buttongroup);
//
//        mGroup = (donate) findViewById(R.id.viewGroup);
//        mSubmitBtn = (Button) findViewById(R.id.submitBtn);
//        bt = (ImageView) findViewById(R.id.back);
//        String text;
//        viewtexts.add("猫粮");
//        viewtexts.add("猫砂");
//        viewtexts.add("猫零食");
//        bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
//            @Override
//            public void onClick(View v){
//                Intent i = new Intent(newdonate.this, MainActivity.class); //切换窗口
//                startActivity(i);
//            }
//        });
//        mGroup.addItemViews(viewtexts, donate.TEV_MODE);
//        mGroup.setGroupClickListener(this);
//        mSubmitBtn.setOnClickListener(this);
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void onGroupItemClick(int item) {
//        chooseID = item;
//        chooseText = mGroup.getChooseText(item);
//    }
//
//    @Override
//    public void onClick(View view) {
//        if (chooseID >= 0) {
//            showToast("ID:" + chooseID + ";text:" + chooseText);
//        } else {
//            showToast("请选择");
//        }
//    }
//
//    private void showToast(String text) {
//        Toast.makeText(newdonate.this, text, Toast.LENGTH_SHORT).show();
//    }
//
//}
