package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lucky.keshe.R;

public class PresentPlanActivity extends AppCompatActivity {
    private ImageView back_present_plan_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present_plan);
        back_present_plan_image=findViewById(R.id.back_present_plan_image);
        back_present_plan_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PresentPlanActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
