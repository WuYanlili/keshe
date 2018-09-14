package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import com.example.lucky.keshe.R;


public class MainActivity extends AppCompatActivity {
    private Button bt_make_plan;
    private Button bt_history_plan;
    private Button bt_make_publish;
    private Button bt_present_plan;
    private Button bt_reviser_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_make_plan=findViewById(R.id.bt_make_plan);
        bt_history_plan=findViewById(R.id.bt_history_plan);
        bt_present_plan=findViewById(R.id.bt_present_plan);
        bt_reviser_password=findViewById(R.id.bt_revise_password);
        bt_make_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(MainActivity.this,MakePlanActivity.class);
                startActivity(intent1);
            }
        });
        bt_history_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(MainActivity.this,HistoryPlanActivity.class);
                startActivity(intent2);
            }
        });
        bt_present_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4=new Intent(MainActivity.this,PresentPlanActivity.class);
                startActivity(intent4);
            }
        });
        bt_reviser_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5=new Intent(MainActivity.this,ReviserPasswordActivity.class);
                startActivity(intent5);
            }
        });


    }
}
