package com.example.rail.ui.activity;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.example.rail.MyDatabaseHelper;
import com.example.rail.R;
import com.example.rail.db.LoginUser;
import com.example.rail.db.User;
import com.example.rail.db.order;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class UserService {
    public User user;
    private MyDatabaseHelper dbHelper;

    Context context;
    SQLiteDatabase sdb;
    public UserService(Context context){
        dbHelper=new MyDatabaseHelper(context);
        this.context=context;
    }

    public boolean login(String username,String password){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
 //       String sql="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";
        //      sdb.execSQL(sql);
 //       String
        String sql="select * from user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
                int id = new Integer(cursor.getString(0));
                String uname = cursor.getString(1);
                int age = new Integer(cursor.getString(3));
                String sex = cursor.getString(4);
                User user = new User();
                user.setUsername(uname);
                System.out.println(id);
                System.out.println(uname);
                System.out.println(age);
                System.out.println(sex);
                user.setId(id);
                user.setSex(sex);
                user.setAge(age);
                LoginUser.getInstance().login(user);
            cursor.close();
           // user = new User(cursor.getString(cursor.getColumnIndex("username")),cursor.getString(cursor.getColumnIndex("password")),cursor.getInt(cursor.getColumnIndex("age")),cursor.getString(cursor.getColumnIndex("sex")));
            return true;
        }
        return false;
    }
    public boolean register(User user){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
//        String sql1="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";
        Drawable drawable1 = context.getResources().getDrawable(R.drawable.my_cat);
        BitmapDrawable bd1 = (BitmapDrawable) drawable1;
        final Bitmap bitmap1 = bd1.getBitmap();
        String sql="insert into user(username,password,age,sex,image) values(?,?,?,?,?)";
        Object obj[]={user.getUsername(),user.getPassword(),user.getAge(),user.getSex(),bitmabToBytes(bitmap1)};
        sdb.execSQL(sql, obj);
        return true;
    }
    public boolean setorder(order or){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String s1=or.get_date();
        String s2=or.get_startdestination();
        String s3=or.get_enddestinatioin();
        String s4=or.get_useTime();

        String sql="insert into orderr(date,startdestination,startdestination ,usetime) values(?,?,?,?)";
        Object obj[]={or.get_date(),or.get_startdestination(),or.get_enddestinatioin(),or.get_useTime()};
        sdb.execSQL(sql, obj);
        return true;
    }
//    public boolean donate(donation user){
//        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
////        String sql1="create table user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2))";
////        sdb.execSQL(sql1);
//        String sql="update donation set maoliang=?,maobohe=?,maosha=?,maoguantou=? where userid=?";
//        Object obj[]={user.getuserid(),user.getmaoliang(),user.getmaobohe(),user.getmaosha(),user.getmaoguantou()};
//        sdb.execSQL(sql, obj);
//        return true;
//    }
    public void inittable(){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
//        String sql5 ="drop table if exists user";
//        sdb.execSQL(sql5);
//        String sql6 ="drop table if exists cat";
//        sdb.execSQL(sql6);
//        String sql7 ="drop table if exists donation";
//        sdb.execSQL(sql7);
//        String sql8 ="drop table if exists focus";
//        sdb.execSQL(sql8);
        String sql1="create table if not exists user(id integer primary key autoincrement,username varchar(20),password varchar(20),age integer,sex varchar(2),image BLOB)";
        sdb.execSQL(sql1);
//        String sql12="alter table user  ADD COLUMN image BLOB";
//        sdb.execSQL(sql12);
        String sql2="create table if not exists cat(id integer primary key autoincrement,catname varchar(20),maose varchar(20),birthdate varchar(20),sex varchar(2),condition varchar(20),character varchar(100),image BLOB)";
        sdb.execSQL(sql2);
        String sql3="create table if not exists donation(userid integer, maoliang integer,maobohe integer,maosha integer,maoguantou integer,foreign key(userid) REFERENCES user(id))";
        sdb.execSQL(sql3);
        String sql4="create table if not exists focus(userid integer,catid integer,foreign key(userid) REFERENCES user(id),foreign key(catid) REFERENCES cat(id))";
        sdb.execSQL(sql4);

    }

    public void  initcat(){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        Drawable drawable1 = context.getResources().getDrawable(R.drawable.buou);
        BitmapDrawable bd1 = (BitmapDrawable) drawable1;
        final Bitmap bitmap1 = bd1.getBitmap();
        Drawable drawable2 = context.getResources().getDrawable(R.drawable.meiduan);
        BitmapDrawable bd2 = (BitmapDrawable) drawable2;
        final Bitmap bitmap2 = bd2.getBitmap();
        Drawable drawable3 = context.getResources().getDrawable(R.drawable.zheermao);
        BitmapDrawable bd3 = (BitmapDrawable) drawable3;
        final Bitmap bitmap3 = bd3.getBitmap();
        Drawable drawable4 = context.getResources().getDrawable(R.drawable.yingduan);
        BitmapDrawable bd4 = (BitmapDrawable) drawable4;
        final Bitmap bitmap4 = bd4.getBitmap();
        Drawable drawable5 = context.getResources().getDrawable(R.drawable.dayanbao);
        BitmapDrawable bd5 = (BitmapDrawable) drawable5;
        final Bitmap bitmap5 = bd5.getBitmap();
        Drawable drawable6 = context.getResources().getDrawable(R.drawable.heimaojingzhang);
        BitmapDrawable bd6 = (BitmapDrawable) drawable6;
        final Bitmap bitmap6 = bd6.getBitmap();
        Drawable drawable7 = context.getResources().getDrawable(R.drawable.maolv);
        BitmapDrawable bd7 = (BitmapDrawable) drawable7;
        final Bitmap bitmap7 = bd7.getBitmap();
        Drawable drawable8 = context.getResources().getDrawable(R.drawable.dahua);
        BitmapDrawable bd8 = (BitmapDrawable) drawable8;
        final Bitmap bitmap8 = bd8.getBitmap();
        Drawable drawable9 = context.getResources().getDrawable(R.drawable.shiliu);
        BitmapDrawable bd9 = (BitmapDrawable) drawable9;
        final Bitmap bitmap9 = bd9.getBitmap();
        Drawable drawable10 = context.getResources().getDrawable(R.drawable.sanni);
        BitmapDrawable bd10 = (BitmapDrawable) drawable10;
        final Bitmap bitmap10 = bd10.getBitmap();
        String sql="insert into cat(catname,maose,birthdate,sex,condition,character,image) values(?,?,?,?,?,?,?)";

        Object obj[]={"大橘","橘色","2019-01-01","公","在校","黑白相间",bitmabToBytes(bitmap1)};
        Object obj1[]={"大黑","黑色","2019-01-02","母","在校","黑橘相间",bitmabToBytes(bitmap2)};
        Object obj2[]={"大白","白色","2019-01-03","公","毕业","橘白相间",bitmabToBytes(bitmap3)};
        Object obj3[]={"大黄","黄色","2019-01-04","公","喵星","白黑相间",bitmabToBytes(bitmap4)};
        Object obj4[]={"大眼猫","橘色","2019-06-27","公","在校","脸上有白色条纹",bitmabToBytes(bitmap5)};
        Object obj5[]={"黑猫警长","黑灰色","2019-05-29","公","毕业","颜色偏深",bitmabToBytes(bitmap6)};
        Object obj6[]={"毛驴","黑白色","2020-04-19","公","休学","长得像驴",bitmabToBytes(bitmap7)};
        Object obj7[]={"大华","黄白相间","2019-02-18","母","休学","手握樱桃",bitmabToBytes(bitmap8)};
        Object obj8[]={"十六","白黄黑","2018-11-30","公","喵星 ","长得吓人",bitmabToBytes(bitmap9)};
        Object obj9[]={"三妮","黄相间","2020-10-03","母","喵星","挺黄的",bitmabToBytes(bitmap10)};

        sdb.execSQL(sql, obj);
        sdb.execSQL(sql, obj1);
        sdb.execSQL(sql, obj2);
        sdb.execSQL(sql, obj3);
        sdb.execSQL(sql, obj4);
        sdb.execSQL(sql, obj5);
        sdb.execSQL(sql, obj6);
        sdb.execSQL(sql, obj7);
        sdb.execSQL(sql, obj8);
        sdb.execSQL(sql, obj9);

    }


    //图片转为二进制数据
    public byte[] bitmabToBytes(Bitmap bitmap){
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        //创建一个字节数组输出流,流的大小为size
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            return imagedata;
        }catch (Exception e){
        }finally {
            try {
                bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
    public void update(){

    }
}
