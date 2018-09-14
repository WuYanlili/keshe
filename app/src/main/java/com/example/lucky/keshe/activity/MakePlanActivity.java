package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lucky.keshe.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MakePlanActivity extends AppCompatActivity {
    private ImageView back_make_plan_image;
    public EditText plan_name;
    public EditText plan_content;
    public EditText starting_time;
    public EditText deadline;
    public Button save_plan;
    String name,content,timestart,timelast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);
        String[] ctype = new String[]{"选择惩罚", "发送朋友圈", "qq发送"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ctype);  //创建一个数组适配器
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     //设置下拉列表框的下拉选项样式

        Spinner identify = super.findViewById(R.id.identify);
        identify.setAdapter(adapter);
        back_make_plan_image=findViewById(R.id.back_make_plan_image);
        back_make_plan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MakePlanActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        save_plan=findViewById(R.id.save_plan);
        save_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_name=findViewById(R.id.plan_name);
                name=plan_name.getText().toString();
                plan_content=findViewById(R.id.plan_content);
                content=plan_content.getText().toString();
                starting_time=findViewById(R.id.starting_time);
                timestart=starting_time.getText().toString();
                deadline=findViewById(R.id.deadline);
                timelast=deadline.getText().toString();
                new Thread(){
                    public void run(){
                        init(name,content,timestart,timelast);
                            Intent it=new Intent(MakePlanActivity.this,MainActivity.class);
                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(it);
                        }
                }.start();
            }
        });
    }

    //json格式与服务点交互
    private void init(String name, String content, String timestart, String timelast) {
        String urlPath="http://192.168.57.2:8080";
        URL url;
        BufferedReader reader=null;
        int id=0;
        try{
            url=new URL(urlPath);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            DataOutputStream out=new DataOutputStream(conn.getOutputStream());
            out.writeBytes("name=study&content=studyupup&start_time=12.00&during_time=13.00");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
