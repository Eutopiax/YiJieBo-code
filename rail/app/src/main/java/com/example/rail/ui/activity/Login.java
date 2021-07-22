package com.example.rail.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rail.MainActivity;
import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Login extends AppCompatActivity {
    private CheckBox checkBox;
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//即activity_login.xml

        findViews();

        UserService uService=new UserService(Login.this);
        uService.inittable();
        String sql1="select * from cat ";
        // com.test.db 是程序的包名，请根据自己的程序调整
        // /data/data/com.test.db/
        // databases 目录是准备放 SQLite 数据库的地方，也是 Android 程序默认的数据库存储目录
        // 数据库名为 test.db
        String DB_PATH = "/data/data/com.example.cat_caring/databases/";
        String DB_NAME = "user.db";

        // 检查 SQLite 数据库文件是否存在
        if ((new File(DB_PATH + DB_NAME)).exists() == false) {
            // 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
            File f = new File(DB_PATH);
            // 如 database 目录不存在，新建该目录
            if (!f.exists()) {
                f.mkdir();
            }

            try {
                // 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
                InputStream is = getBaseContext().getAssets().open(DB_NAME);
                // 输出流
                OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);

                // 文件写入
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                // 关闭文件流
                os.flush();
                os.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MyDatabaseHelper dbHelper=new MyDatabaseHelper(this);
        SQLiteDatabase sdb=dbHelper.getWritableDatabase();
        Cursor cursor=sdb.rawQuery(sql1,null);
        if(!cursor.moveToNext()){
            uService.initcat();
        }
    }

    private void findViews() {
        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        register=(Button) findViewById(R.id.register);
        checkBox = (CheckBox) findViewById(R.id.cb_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{
                    //否则隐藏密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                System.out.println(name);
                String pass=password.getText().toString();
                System.out.println(pass);
                User us = new User();
                Log.i("TAG",name+"_"+pass);
                UserService uService=new UserService(Login.this);
                boolean flag=uService.login(name, pass);

                if(flag){
                    Log.i("TAG","登录成功");
                    Toast.makeText(Login.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Log.i("TAG","登录失败");
                    Toast.makeText(Login.this, "登录失败", Toast.LENGTH_LONG).show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
}