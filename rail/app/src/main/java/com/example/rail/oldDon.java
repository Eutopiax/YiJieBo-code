package com.example.rail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rail.util.ActivityCollector;

public class oldDon  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.olddon);
        ImageView bt = findViewById(R.id.backk);
        bt.setOnClickListener(new View.OnClickListener(){  //点击按钮监听
            @Override
            public void onClick(View v){
                Intent i = new Intent(oldDon.this, MainActivity.class); //切换窗口
                startActivity(i);
            }
        });
    }
}
