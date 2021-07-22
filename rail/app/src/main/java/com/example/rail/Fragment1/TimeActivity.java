package com.example.rail.Fragment1;


import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.rail.R;

public class TimeActivity extends Activity {
    private TextView textview;
    private TimePicker timepicker;
    private DatePicker datepicker;
    /* 声明日期及时间变量*/
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time);
        /*获取当前日期及时间 */
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);

        timepicker = (TimePicker) findViewById(R.id.timepicker);
        /* 设置TextView对象，显示初始日期时间 */
        textview = (TextView) findViewById(R.id.timeview);
        textview.setText(new StringBuilder().append(year).append("/")
                .append(format(month + 1)).append("/")
                .append(format(day)).append(" ")
                .append(format(hour)).append(":")
                .append(format(minute)));
        /* 设置OnDateChangedListener()*/
        datepicker.init(year, month, day,
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        TimeActivity.this.year = year;
                        month = monthOfYear;
                        day = dayOfMonth;
                        textview.setText(new StringBuilder().append(year).
                                append("/").append(format(month + 1))
                                .append("/").append(format(day)).append(" ")
                                .append(format(hour)).append(":")
                                .append(format(minute)));
                    }
                });
        timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO Auto-generated method stub
                hour = hourOfDay;
                TimeActivity.this.minute = minute;
                textview.setText(new StringBuilder().append(year)
                        .append("/").append(format(month + 1))
                        .append("/").append(format(day)).append(" ")
                        .append(format(hour)).append(":")
                        .append(format(minute)));
            }
        });
    }
    private String format(int time) {
        String str = "" + time;
        if (str.length() == 1)
            str = "0" + str;
        return str;
    }
}