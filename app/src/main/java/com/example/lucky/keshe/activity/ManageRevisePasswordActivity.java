package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lucky.keshe.NetUtils;
import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ManageRevisePasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ManageRevisePasswordAct";
    private ImageView back;
    private EditText id;
    private EditText pwd;
    private EditText pwd2;
    private Button reviser;
    String name,password,password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_revise_password);
        back=findViewById(R.id.back_revise_password_manage);
        back.setOnClickListener(this);
        reviser=findViewById(R.id.reviser_btn_manage);
        reviser.setOnClickListener(this);
        id=findViewById(R.id.username_reviser_manage);
        pwd=findViewById(R.id.password_reviser_manage);
        pwd2=findViewById(R.id.twice_password_reviser_manage);
    }

    @Override
    public void onClick(View v) {
        Intent intent0=getIntent();
        final int data=intent0.getIntExtra("user_id",0);
        switch (v.getId()){
            case R.id.back_revise_password_manage:
                Intent intent=new Intent(ManageRevisePasswordActivity.this,ManageActivity.class);
                intent.putExtra("user_id",data);
                Log.i(TAG, "onClick: "+data);
                startActivity(intent);
                finish();
                break;
            case R.id.reviser_btn_manage:
                name=id.getText().toString();
                password=pwd.getText().toString();
                password2=pwd2.getText().toString();
                if (name.length()==0||password.length()==0||password2.length()==0){
                    Toast.makeText(ManageRevisePasswordActivity.this,"用户名密码不能为空",Toast.LENGTH_LONG).show();
                }if (!password2.equals(password)){
                    Toast.makeText(ManageRevisePasswordActivity.this,"两次输入的密码不相同",Toast.LENGTH_LONG).show();
            }else {


                new Thread(){
                    @Override
                    public void run() {
                        int num=init(name,password);
                        if (num>0){
                            Looper.prepare();
                            Toast.makeText(ManageRevisePasswordActivity.this,"修改密码成功",Toast.LENGTH_LONG).show();

                            Intent it=new Intent(ManageRevisePasswordActivity.this,ManageActivity.class);
                            Log.i(TAG, "run: "+it);
                            it.putExtra("user_id",data);
                            startActivity(it);
                            Looper.loop();
                            finish();

                        }
                    }
                }.start();
            }
                break;
        }
    }
    private int init(String name, String pwd) {
        String urlPath = "http://192.168.56.1:8080/WorkingAssistant/changePassword.action";
        URL url;
        int id = 0;
        try {
            url = new URL(urlPath);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", name);  //参数put到json串里
            jsonObject.put("password", pwd);

            //JSONObject Authorization =new JSONObject();
            //   Authorization.put("po类名",jsonObject 即po的字段)

            String content = String.valueOf(jsonObject);  //json串转string类型

            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(8000);

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type", "application/json");
            //写输出流，将要转的参数写入流里
            OutputStream os = conn.getOutputStream();
            os.write(content.getBytes()); //字符串写进二进流
            os.close();

            int code = conn.getResponseCode();
            Log.i(TAG, "init: code:" + code);
            if (code == 200) {   //与后台交互成功返回 200
                //读取返回的json数据
                InputStream inputStream = conn.getInputStream();
                // 调用自己写的NetUtils() 将流转成string类型
                String json = NetUtils.readString(inputStream);

                Gson gson = new Gson();  //引用谷歌的json包
                User user = gson.fromJson(json, User.class); //谷歌的解析json的方法
                Log.i(TAG, "init: " + user);
                id = 1;

            } else {
                Looper.prepare();
                Toast.makeText(getApplicationContext(), "数据提交失败", Toast.LENGTH_LONG).show();
                Looper.loop();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
