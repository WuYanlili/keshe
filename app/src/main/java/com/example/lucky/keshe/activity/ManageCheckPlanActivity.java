package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lucky.keshe.NetUtils;
import com.example.lucky.keshe.R;
import com.example.lucky.keshe.adapter.EmployeeAdapter;
import com.example.lucky.keshe.data.bean.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ManageCheckPlanActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageCheckPlanActivity";
    private List<User> mUsers=new ArrayList<>();
    private ImageView back;
    private EmployeeAdapter employeeAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_check_plan);
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);

        back=findViewById(R.id.back_check_plan_manage);
        back.setOnClickListener(this);
        initView();
        initData();
    }

    private void initData() {
        Log.i(TAG, "initData: listView");
        mUsers=new ArrayList<User>();
        new Thread(){
            @Override
            public void run() {
                String urlPath="http://192.168.56.1:8080/WorkingAssistant/getAllUsers.action";
                URL url;
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url(urlPath).build();
                    Response response=client.newCall(request).execute();
                    String responseData=response.body().string();
                    Gson gson=new Gson();
                    List<User> userList=gson.fromJson(responseData,new TypeToken<List<User>>(){}.getType());
                    for (User user:userList){
                        Log.i(TAG, "run: "+user.getId());
                        mUsers.add(user);
                    }
                    Log.i(TAG, "run: "+mUsers);
                    showUI(mUsers);

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();
        //User user=new User(2);
        //mUsers.add(user);

    }

    private void initView() {
        listView=findViewById(R.id.user_list);
    }

    @Override
    public void onClick(View v) {
        Intent intent0=getIntent();
        int data=intent0.getIntExtra("user_id",0);
        switch (v.getId()){
            case R.id.back_check_plan_manage:
                Intent intent=new Intent(ManageCheckPlanActivity.this,ManageActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);
                finish();
                break;

        }
    }
    private void  showUI(final List<User> mUser){

        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                employeeAdapter=new EmployeeAdapter(ManageCheckPlanActivity.this,mUsers,data);
                listView.setAdapter(employeeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i(TAG, "onItemClick: fdsaf");
                        Toast.makeText(ManageCheckPlanActivity.this,"我是item点击事件 i = " + position + "l = " + id+parent+"   "+view,Toast.LENGTH_SHORT).show();
                        Intent check=new Intent(ManageCheckPlanActivity.this,ViewActivity.class);
                        startActivity(check);
                    }
                });
            }
        });
    }
}
