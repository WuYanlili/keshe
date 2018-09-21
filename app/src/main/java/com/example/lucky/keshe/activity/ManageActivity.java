package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lucky.keshe.R;
import com.yalantis.phoenix.PullToRefreshView;

public class ManageActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageActivity";
    private Button check_plan;
    private Button sign_in;
    private Button manage_make_plan;
    private Button manage_present_plan;
    private Button manage_revise_password;
    private Button manage_history_plan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        check_plan=findViewById(R.id.bt_check_plan);
        check_plan.setOnClickListener(this);
        sign_in=findViewById(R.id.manage_sign_in);
        sign_in.setOnClickListener(this);
        manage_make_plan=findViewById(R.id.bt_manage_make_plan);
        manage_make_plan.setOnClickListener(this);
        manage_present_plan=findViewById(R.id.bt_manage_present_plan);
        manage_present_plan.setOnClickListener(this);
        manage_revise_password=findViewById(R.id.bt_manage_revise_password);
        manage_revise_password.setOnClickListener(this);
        manage_history_plan=findViewById(R.id.bt_manage_history_plan);
        manage_history_plan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        switch (v.getId()){
            case R.id.manage_sign_in:
                String str=sign_in.getText().toString();
                if (str.equals("已    离    开")){
                    sign_in.setText("工    作    中");
                }else {
                    sign_in.setText("已    离    开");
                }
                break;
            case R.id.bt_manage_make_plan:
                Intent intent=new Intent(ManageActivity.this,ManageMakePlanActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);
                break;
            case R.id.bt_manage_history_plan:
                Intent intent1=new Intent(ManageActivity.this,ManageHistoryPlanActivity.class);
                intent1.putExtra("user_id",data);
                startActivity(intent1);
                break;
            case R.id.bt_manage_present_plan:
                Intent intent2=new Intent(ManageActivity.this,ManagePresentPlanActivity.class);
                intent2.putExtra("user_id",data);
                startActivity(intent2);
                break;
            case R.id.bt_manage_revise_password:
                Intent intent3=new Intent(ManageActivity.this,ManageRevisePasswordActivity.class);
                intent3.putExtra("user_id",data);
                startActivity(intent3);
                break;
            case R.id.bt_check_plan:
                Intent intent4=new Intent(ManageActivity.this,ManageCheckPlanActivity.class);
                intent4.putExtra("user_id",data);
                startActivity(intent4);
                break;
            default:
                    break;
        }
    }
}
