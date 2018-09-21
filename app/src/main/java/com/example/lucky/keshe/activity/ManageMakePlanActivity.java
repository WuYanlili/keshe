package com.example.lucky.keshe.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.example.lucky.keshe.R;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManageMakePlanActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageMakePlanActivity";
    private ImageView back;
    public EditText plan_name;
    public EditText plan_content;
    public EditText starting_time;
    public EditText deadline;
    public Button bt_staring_time;
    public Button bt_deadline;
    public Button save_plan;
    String name,content,timestart;
    private int hourOfDay,minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_make_plan);
        back=findViewById(R.id.back_make_plan_manage);
        bt_staring_time=findViewById(R.id.bt_starting_time_manage);
        bt_deadline=findViewById(R.id.bt_deadline_manage);
        Calendar calendar = Calendar.getInstance();
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        starting_time=findViewById(R.id.starting_time_manage);
        deadline=findViewById(R.id.deadline_manage);
        save_plan=findViewById(R.id.save_plan_manage);
        back.setOnClickListener(this);
        bt_staring_time.setOnClickListener(this);
        bt_deadline.setOnClickListener(this);
        save_plan.setOnClickListener(this);
        plan_name=findViewById(R.id.plan_name_manage);
        plan_content=findViewById(R.id.plan_content_manage);
        name=plan_name.getText().toString();
        content=plan_content.getText().toString();
        timestart=starting_time.getText().toString();
    }

    @Override
    public void onClick(View v) {
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        switch (v.getId()){
            case R.id.back_make_plan_manage:
                Intent intent=new Intent(ManageMakePlanActivity.this,ManageActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);
                finish();
                break;
            case R.id.bt_starting_time_manage:
                TimePickerDialog timePickerDialog=new TimePickerDialog(ManageMakePlanActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        starting_time.setText("Time: " + hourOfDay + ":" + minute);
                    }
                },hourOfDay, minute, true);
                timePickerDialog.show();
                break;
            case R.id.bt_deadline_manage:
                break;
            case R.id.save_plan_manage:
                final String s=Integer.toString(data);
                final String urlPath="http://192.168.56.1:8080/WorkingAssistant/createPlan.action";
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client=new OkHttpClient();
                            RequestBody requestBody=new FormBody.Builder().add("user_id",s).add("name",name)
                                    .add("content",content).add("begin_time",timestart).build();
                            Log.i(TAG, "run: requestBody"+requestBody);
                            Request request=new Request.Builder().url(urlPath).post(requestBody).build();
                            Response response=client.newCall(request).execute();
                            Log.i(TAG, "run: response"+response);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }
}
