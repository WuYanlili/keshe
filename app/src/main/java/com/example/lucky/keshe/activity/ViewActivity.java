package com.example.lucky.keshe.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.MonthBean;
import com.example.lucky.keshe.fragment.PieFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager vpMain;
    private String mJson="[{\"date\":\"2018.5\",\"obj\":[{\"title\":\"已完成\"," +
            "\"value\":80},{\"title\":\"未完成\",\"value\":8}]},{\"date\":\"2018.6\",\"obj\":" +
            "[{\"title\":\"已完成\",\"value\":34},{\"title\":\"未完成\",\"value\":21}]},{\"date" +
            "\":" + "\"2018.7\",\"obj\":" + "[{\"title\":\"已完成\",\"value\":20}," +
            "{\"title\":\"未完成\",\"value\":30}]}]";
    private ArrayList<MonthBean> mData;
    private Button btPre;
    private Button btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        vpMain=findViewById(R.id.vp_main);
        btPre = (Button) findViewById(R.id.bt_pre);
        btNext = (Button) findViewById(R.id.bt_next);

        btPre.setOnClickListener(this);
        btNext.setOnClickListener(this);
        initDate();
        initView();
    }

    private void initDate() {
        Gson gson=new Gson();
        mData = gson.fromJson(mJson, new TypeToken<ArrayList<MonthBean>>() {
        }.getType());
    }

    private void initView() {
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
        updateJumpText();
    }

    private void updateJumpText() {
        if (vpMain.getCurrentItem() != vpMain.getAdapter().getCount()-1) {
            btNext.setText(handleText(mData.get(vpMain.getCurrentItem()+1).date));
        } else {
            btNext.setText("没有了！");
        }
        if (vpMain.getCurrentItem() != 0) {
            btPre.setText(handleText(mData.get(vpMain.getCurrentItem()-1).date));
        } else {
            btPre.setText("没有了！");
        }
    }

    private CharSequence handleText(String date) {
        return date.substring(date.indexOf("年")+1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next:
                if (vpMain.getCurrentItem() != vpMain.getAdapter().getCount()){
                    vpMain.setCurrentItem(vpMain.getCurrentItem()+1);
                }
                break;
            case R.id.bt_pre:
                if (vpMain.getCurrentItem() != 0){
                    vpMain.setCurrentItem(vpMain.getCurrentItem()-1);
                }
        }
        updateJumpText();
    }
}
