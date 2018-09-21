package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;

import com.example.lucky.keshe.HttpInterface;
import com.example.lucky.keshe.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button bt_make_plan;
    private Button bt_history_plan;
    private Button bt_present_plan;
    private Button bt_reviser_password;
    private Button sing_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        final int data=intent.getIntExtra("user_id",0);
        Log.i(TAG, "onCreate: "+data);
        bt_make_plan=findViewById(R.id.bt_make_plan);
        bt_history_plan=findViewById(R.id.bt_history_plan);
        bt_present_plan=findViewById(R.id.bt_present_plan);
        sing_in=findViewById(R.id.sign_in);
        bt_reviser_password=findViewById(R.id.bt_revise_password);

        bt_make_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: "+data);
                Intent intent1=new Intent(MainActivity.this,MakePlanActivity.class);
                intent1.putExtra("user_id",data);
                startActivity(intent1);
            }
        });
        bt_history_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,HistoryPlanActivity.class);
                intent2.putExtra("user_id",data);
                startActivity(intent2);
            }
        });
        bt_present_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(MainActivity.this,PresentPlanActivity.class);
                intent4.putExtra("user_id",data);
                startActivity(intent4);
            }
        });
        bt_reviser_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(MainActivity.this,ReviserPasswordActivity.class);
                intent5.putExtra("user_id",data);
                startActivity(intent5);
            }
        });
        sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSingin();
            }
        });



    }

    private void initSingin() {
        String str=sing_in.getText().toString();
        if (str.equals("已    离    开")){
            sing_in.setText("工    作    中");
        }else {
            sing_in.setText("已    离    开");
        }
    }
}
