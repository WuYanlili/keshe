package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.Plan;

import java.util.ArrayList;
import java.util.List;

public class HistoryPlanActivity extends AppCompatActivity {
    private ImageView back_history_plan_image;
    private List<Plan> planList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_plan);
        back_history_plan_image=findViewById(R.id.back_history_plan_image);
        back_history_plan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HistoryPlanActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void initPlan(){
        for (int i=0;i<2;i++){

        }
    }
}
