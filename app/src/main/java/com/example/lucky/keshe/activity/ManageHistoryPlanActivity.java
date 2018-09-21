package com.example.lucky.keshe.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.adapter.HistoryPlanAdapter;
import com.example.lucky.keshe.adapter.ManageHistoryPlanAdapter;
import com.example.lucky.keshe.data.bean.Plan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManageHistoryPlanActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageHistoryPlanActivity";
    private ImageView back;
    private ListView listView;
    private List<Plan> mPlans;
    private ManageHistoryPlanAdapter manageHistoryPlanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_history_plan);

        back=findViewById(R.id.back_history_plan_manage);
        back.setOnClickListener(this);
        initView();
        initData();
    }

    private void initData() {
        mPlans=new ArrayList<Plan>();
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
       // Plan plan=new Plan("学习");
        //mPlans.add(plan);
        final String urlPath="http://192.168.56.1:8080/WorkingAssistant/getAllPlans.action";
        final String s=Integer.toString(data);
        new Thread(new Runnable() {
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().add("user_id",s).build();
                    Request request=new  Request.Builder().url(urlPath).post(requestBody).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Gson gson=new Gson();
                    List<Plan> planList=gson.fromJson(responseData,new TypeToken<List<Plan>>(){}.getType());
                    for (Plan plan:planList){
                        Log.i(TAG, "run: "+plan);
                        planList.add(plan);
                    }
                    showUI(planList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        manageHistoryPlanAdapter=new ManageHistoryPlanAdapter(this,mPlans,data);
        listView.setAdapter(manageHistoryPlanAdapter);
    }

    private void initView() {
        listView =findViewById(R.id.show_history_plan_manage);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        switch (v.getId()){
            case R.id.back_history_plan_manage:
                Intent intent=new Intent(ManageHistoryPlanActivity.this,ManageActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);
                finish();
                break;
        }
    }
    private void  showUI(final List<Plan> mPlans) {
        Intent intent0 = getIntent();
        final int data = intent0.getIntExtra("user_id", 0);
        manageHistoryPlanAdapter=new ManageHistoryPlanAdapter(ManageHistoryPlanActivity.this,mPlans,data);
        listView.setAdapter(manageHistoryPlanAdapter);

    }

}
