package com.example.healthapplication;


import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.litepal.util.Const;
import org.litepal.util.Const.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private RadioGroup mTabRadioGroup;

    private List<Fragment> mFragments;

    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }
    private void initView() {

        // find view

        mViewPager = findViewById(R.id.fragment_vp);

        mTabRadioGroup = findViewById(R.id.tabs_rg);

        // init fragment

        mFragments = new ArrayList<>(4);

        mFragments.add(QandAFragment.newInstance());

        mFragments.add(HealthyTipFragment.newInstance());

        mFragments.add(AnalyzeFragment.newInstance());

        mFragments.add(PersonalinfoFragment.newInstance());

        // init view pager

        mAdapter = new MainActivity.MyFragmentPagerAdapter(getSupportFragmentManager(), mFragments);

        mViewPager.setAdapter(mAdapter);

        // register listener

        mViewPager.addOnPageChangeListener(mPageChangeListener);

        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("健康管家");
//        setSupportActionBar(toolbar);

    }


    @Override

    protected void onDestroy() {

        super.onDestroy();

        mViewPager.removeOnPageChangeListener(mPageChangeListener);

    }



    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



        }



        @Override

        public void onPageSelected(int position) {

            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);

            radioButton.setChecked(true);

        }



        @Override

        public void onPageScrollStateChanged(int state) {



        }

    };



    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override

        public void onCheckedChanged(RadioGroup group, int checkedId) {

            for (int i = 0; i < group.getChildCount(); i++) {

                if (group.getChildAt(i).getId() == checkedId) {

                    mViewPager.setCurrentItem(i);

                    return;

                }

            }

        }

    };



    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {



        private List<Fragment> mList;



        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {

            super(fm);

            this.mList = list;

        }



        @Override

        public Fragment getItem(int position) {

            return this.mList == null ? null : this.mList.get(position);

        }



        @Override

        public int getCount() {

            return this.mList == null ? 0 : this.mList.size();

        }

    }


}

