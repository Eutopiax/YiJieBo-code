package com.example.rail.ui.locserv;

import android.util.Log;

import com.example.rail.ui.activity.location;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;



public class Send_InputStream implements Runnable {

    private InputStream inputStream;
    private location mainActivity;
    private String text = "null";

    public Send_InputStream(){}
    public Send_InputStream(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public void setMainActivity(location mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void run() {
         int len = 0;
        byte[] buff = new byte[1024];
        try{
            while ((len = inputStream.read(buff)) != -1){
                text = new String(buff,0,len);
                Log.i("129",text+"返回值");
                JSONObject jsonObject = new JSONObject(text);
                int errorCode = jsonObject.getInt("errorCode");
                if (errorCode == 200){
                    JSONArray jsonArray = jsonObject.getJSONArray("date");
                    mainActivity.allLatLng(jsonArray);
                }
            }
        }catch (Exception e){
            Log.i("129",e.getMessage()+"接收返回值报错");
        }
    }
}
