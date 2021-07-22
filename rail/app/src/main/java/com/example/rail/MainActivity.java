package com.example.rail;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rail.Fragment1.Fragment1;
import com.example.rail.Fragment2.Fragment2;
import com.example.rail.ui.fragment.PersonPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private PersonPageFragment fragment3;
    private Fragment[] fragments;
    private int lastfragment;//用于记录上个选择的Fragment
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        dbHelper = new MyDatabaseHelper(this,"User.db",null,1);
//        dbHelper.getWritableDatabase();
        initFragment();
        //初始化fragment和fragment数组
    }

    private void initFragment() {

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new PersonPageFragment();
        fragments = new Fragment[]{fragment1, fragment2, fragment3};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainview, fragment1).show(fragment1).commit();
        bottomNavigationView = findViewById(R.id.bnv);

        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    //判断选择的菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.id1: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;

                    }

                    return true;
                }
                case R.id.id2: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;

                    }

                    return true;
                }
                case R.id.id3: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;

                    }

                    return true;
                }


            }


            return false;
        }
    };

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainview, fragments[index]);


        }
        transaction.show(fragments[index]).commitAllowingStateLoss();


    }
}