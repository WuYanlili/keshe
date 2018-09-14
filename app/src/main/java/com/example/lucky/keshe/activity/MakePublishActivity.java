package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lucky.keshe.R;

public class MakePublishActivity extends AppCompatActivity {
    private ImageView back_make_publish_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_publish);
        back_make_publish_image=findViewById(R.id.back_make_publish_image);
        back_make_publish_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MakePublishActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
