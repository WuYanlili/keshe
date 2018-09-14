package com.example.lucky.keshe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lucky.keshe.R;
import com.yalantis.phoenix.PullToRefreshView;

public class ManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        final PullToRefreshView mPullToRefreshView = findViewById(R.id.pull_to_refresh);
        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.setRefreshing(false);
                    }
                },1000);
            }
        });
    }
}
