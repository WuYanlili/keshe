package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucky.keshe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManagePresentPlanActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManagePresentPlanActivi";
    private ImageView back;
    private TextView tv_timer;
    private TextView present_plan_tv;
    private Button complete_plan;
    private TextView deadline_timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_present_plan);
        new TimeThread().start();
        back=findViewById(R.id.back_present_plan_manage);
        back.setOnClickListener(this);
        complete_plan=findViewById(R.id.complete_plan_manage);
        present_plan_tv=findViewById(R.id.present_plan_tv_manage);
        present_plan_tv.setText("你的计划！！！");
        deadline_timer=findViewById(R.id.deadline_timer_manage);
        new Thread(new Runnable() {
            @Override
            public void run() {
                deadline_timer.setText("");
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        switch (v.getId()){
            case R.id.back_present_plan_manage:
                Intent intent=new Intent(ManagePresentPlanActivity.this,ManageActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);
                finish();
                break;
            case R.id.complete_plan_manage:
                String startTime=tv_timer.getText().toString();
                String deadline=deadline_timer.getText().toString();
                int i=getTimeSize(startTime,deadline);
                if (i==1){
                    Toast.makeText(this,"恭喜你完成任务",Toast.LENGTH_LONG).show();

                }
                if (i==2){
                    Toast.makeText(this,"恭喜你完成任务",Toast.LENGTH_LONG).show();
                }
                if (i==3){
                    Toast.makeText(this,"计划超时，未完成任务",Toast.LENGTH_LONG).show();
                    complete_plan.setEnabled(false);
                }
                break;
            default:
                break;
        }
    }
    public class TimeThread extends Thread{
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }
    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", sysTime);//时间显示格式
                    tv_timer=findViewById(R.id.tv_timer_manage);
                    tv_timer.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;
            }
        }
    };
    public int getTimeSize(String startTime,String endTime){
        int i=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");//时-分-秒
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                i= 1;
            }else if (date2.getTime()==date1.getTime()){
                i= 2;
            }else if (date2.getTime()>date1.getTime()){
                //正常情况下的逻辑操作.
                i= 3;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  i;

    }
}
