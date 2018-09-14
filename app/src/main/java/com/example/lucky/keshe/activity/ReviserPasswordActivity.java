package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.lucky.keshe.R;

public class ReviserPasswordActivity extends AppCompatActivity {
    private ImageView back_reviser_password_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviser_password);
        back_reviser_password_image=findViewById(R.id.back_reviser_password_image);
        back_reviser_password_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReviserPasswordActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
