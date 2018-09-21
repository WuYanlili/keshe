package com.example.lucky.keshe.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lucky.keshe.NetUtils;
import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.Plan;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MakePlanActivity extends AppCompatActivity {
    private static final String TAG = "MakePlanActivity";
    private ImageView back_make_plan_image;
    public EditText plan_name;
    public EditText plan_content;
    public EditText starting_time;
    public EditText deadline;
    public Button bt_staring_time;
    public Button bt_deadline;
    public Button save_plan;
    String name,content;
    Date timestart,timelast;
    private int hourOfDay,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_plan);
        Intent intent=getIntent();
        final int data=intent.getIntExtra("user_id",0);
        Log.i(TAG, "onCreate: "+data);
        bt_staring_time=findViewById(R.id.bt_starting_time);
        bt_deadline=findViewById(R.id.bt_deadline);
        Calendar calendar = Calendar.getInstance();
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        starting_time=findViewById(R.id.starting_time);
        deadline=findViewById(R.id.deadline);
        bt_staring_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(MakePlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        starting_time.setText(hourOfDay + ":" + minute);
                        Log.i(TAG, "onTimeSet: "+starting_time.getText().toString());
                    }
                },hourOfDay, minute, true);
                timePickerDialog.show();
            }
        });
        bt_deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(MakePlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        deadline.setText(hourOfDay+":"+minute);
                    }
                },hourOfDay,minute,true);
                timePickerDialog.show();
            }
        });

        back_make_plan_image=findViewById(R.id.back_make_plan_image);
        back_make_plan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MakePlanActivity.this,MainActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
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
                Log.i(TAG, "onClick: starting_time"+starting_time.getText().toString());
                final String beginTime=starting_time.getText().toString();
                SimpleDateFormat format1 =  new SimpleDateFormat("yyyy-mm-dd HH:mm");
                try {
                    timestart=format1.parse("2018-09-21 12:01");
                    //timelast=format1.parse(deadline.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                final java.sql.Date timeStart=new java.sql.Date(timestart.getTime());
                Log.i(TAG, "onClick: deadline"+deadline.getText().toString());

                new Thread(){
                    @Override
                    public void run() {
                        Log.i(TAG, "run: run");
                        int num=init(data,name,content,beginTime);
                        Log.i(TAG, "run: data"+data);
                        if (num>0){
                            Intent it=new Intent(MakePlanActivity.this,MainActivity.class);
                            it.putExtra("user_id",data);
                            Log.i(TAG, "onClick: "+data);
                            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(it);
                        }else {
                            Toast.makeText(MakePlanActivity.this,"保存失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }.start();
            }
        });
    }

    private int init(int data,String name,String content,String beginTime) {
        String s=Integer.toString(data);
        String urlPath="http://192.168.56.1:8080/WorkingAssistant/createPlan.action";
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=new FormBody.Builder().add("user_id",s).add("name",name)
                .add("content",content).add("begin_time",beginTime).build();
        Request request=new Request.Builder().url(urlPath).build();
        Log.i(TAG, "init: request"+request);
        try {
            Response response=client.newCall(request).execute();
            Log.i(TAG, "init: response"+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 1;
    }

}
