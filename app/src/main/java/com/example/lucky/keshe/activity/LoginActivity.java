package com.example.lucky.keshe.activity;

import android.content.Intent;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Button login;
    private TextView forget_password;
    private EditText username;
    private EditText password;
    String name,pwd;
    private TextView manage_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.edit_username_login);
        password=findViewById(R.id.edit_password_login);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=username.getText().toString();
                pwd=password.getText().toString();

                new Thread(){
                    @Override
                    public void run() {
                        int num=init(name,pwd);
                        if (num>0){
                            Intent it=new Intent(LoginActivity.this,MainActivity.class);
                            it.putExtra("user_id",num);
                            startActivity(it);
                            finish();
                        }else {
                            Looper.prepare();
                            Toast.makeText (getApplicationContext(),"用户或密码错误", Toast.LENGTH_LONG ).show();
                            Looper.loop();
                        }
                    }
                }.start();
            }
        });
        manage_login=findViewById(R.id.manage_btn_text);
        manage_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manage=new Intent(LoginActivity.this,ManageLoginActivity.class);
                startActivity(manage);
            }
        });
        forget_password=findViewById(R.id.forget_login_text);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(LoginActivity.this,ReviserPasswordActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    private int init(String name, String pwd) {
        String urlPath="http://192.168.56.1:8080/WorkingAssistant/login.action";
        URL url;
        int id=0;
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("id",name);  //参数put到json串里
            jsonObject.put("password",pwd);

            //JSONObject Authorization =new JSONObject();
            //   Authorization.put("po类名",jsonObject 即po的字段)

            String content=String.valueOf(jsonObject);  //json串转string类型

            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(8000);

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type","application/json");
            //写输出流，将要转的参数写入流里
            OutputStream os=conn.getOutputStream();
            os.write(content.getBytes()); //字符串写进二进流
            os.close();

            int code=conn.getResponseCode();
            Log.i(TAG, "init: code:"+code);
            if(code==200){   //与后台交互成功返回 200
                //读取返回的json数据
                InputStream inputStream=conn.getInputStream();
                // 调用自己写的NetUtils() 将流转成string类型
                String json= NetUtils.readString(inputStream);

                Gson gson=new Gson();  //引用谷歌的json包
                User user=gson.fromJson(json,User.class); //谷歌的解析json的方法

                id =user.getId();  //然后user.get你想要的值
                int username=user.getId();
                String password=user.getPassword();

            }else{
                Looper.prepare();
                Toast.makeText (getApplicationContext(),"数据提交失败", Toast.LENGTH_LONG ).show();
                Looper.loop();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  id;

    }

}
