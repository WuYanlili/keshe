package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.adapter.EmployeeAdapter;
import com.example.lucky.keshe.adapter.HistoryPlanAdapter;
import com.example.lucky.keshe.data.bean.Plan;
import com.example.lucky.keshe.data.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HistoryPlanActivity extends AppCompatActivity {
    private static final String TAG = "HistoryPlanActivity";
    private ImageView back_history_plan_image;
    private List<Plan> mPlans;
    private HistoryPlanAdapter historyPlanAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_plan);
        Intent intent=getIntent();
        final int data=intent.getIntExtra("user_id",0);
        Log.i(TAG, "onCreate: "+data);
        back_history_plan_image=findViewById(R.id.back_history_plan_image);
        back_history_plan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryPlanActivity.this,MainActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);

                finish();
            }
        });
        initView();
        initData();

    }

    private void initData() {
        Intent intent=getIntent();
        final int data=intent.getIntExtra("user_id",0);
        mPlans=new ArrayList<Plan>();
        //Plan plan=new Plan("学习");
        //mPlans.add(plan);
        new Thread(){
            @Override
            public void run() {
                String urlPath="http://192.168.56.1:8080/WorkingAssistant/getAllPlans.action";
                String s=Integer.toString(data);
                Log.i(TAG, "run: s"+s);
                try {
                    OkHttpClient client=new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder().add("user_id",s).build();
                    Log.i(TAG, "run: requestBody"+requestBody);
                    Request request=new Request.Builder().url(urlPath).post(requestBody).build();
                    Log.i(TAG, "run: request"+request);
                    Response response=client.newCall(request).execute();
                    Log.i(TAG, "run: response"+response);
                    String responseData=response.body().string();
                    Gson gson=new Gson();
                    JSONObject jsonObject=new JSONObject();
                    List<Plan> planList = gson.fromJson(responseData,new TypeToken<List<Plan>>(){}.getType());
                    for (Plan plan:planList){
                        Log.i(TAG, "run: "+plan.getId());
                        mPlans.add(plan);
                    }
                    Log.i(TAG, "run: "+mPlans);
                    showUI(mPlans);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
        historyPlanAdapter=new HistoryPlanAdapter(this,mPlans,data);
        listView.setAdapter(historyPlanAdapter);

    }

    private void initView() {
        listView =findViewById(R.id.show_history_plan);
    }
    private void  showUI(final List<Plan> mPlans){

        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                HistoryPlanAdapter historyPlanAdapter=new HistoryPlanAdapter(HistoryPlanActivity.this,mPlans,data);
                listView.setAdapter(historyPlanAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(TAG, "onItemClick: fdsaf");
                        Toast.makeText(HistoryPlanActivity.this,"我是item点击事件 i = " + position + "l = " + id,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



}
