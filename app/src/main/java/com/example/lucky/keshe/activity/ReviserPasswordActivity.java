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

import com.example.lucky.keshe.HttpInterface;
import com.example.lucky.keshe.NetUtils;
import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviserPasswordActivity extends AppCompatActivity {
    private static final String TAG = "ReviserPasswordActivity";
    private ImageView back_reviser_password_image;
    private EditText id;
    private EditText password;
    private EditText twicePassword;
    private Button reviser;
    String name,pwd,pwd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviser_password);
        Intent intent=getIntent();
        final int data=intent.getIntExtra("user_id",0);
        Log.i(TAG, "onCreate: "+data);
        id=findViewById(R.id.username_reviser);
        password=findViewById(R.id.password_reviser);
        twicePassword=findViewById(R.id.twice_password_reviser);
        reviser=findViewById(R.id.reviser_btn);
        back_reviser_password_image=findViewById(R.id.back_reviser_password_image);
        back_reviser_password_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReviserPasswordActivity.this,MainActivity.class);
                intent.putExtra("user_id",data);
                startActivity(intent);
                finish();
            }
        });
        reviser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = id.getText().toString();
                pwd = password.getText().toString();
                pwd2 = twicePassword.getText().toString();
                if (name.length()==0 || pwd.length()==0 || pwd2.length()==0) {
                    Toast.makeText(ReviserPasswordActivity.this, "用户名密码不能为空", Toast.LENGTH_LONG).show();
                }
                if (!pwd2.equals(pwd)) {
                    Toast.makeText(ReviserPasswordActivity.this, "两次输入密码不一样", Toast.LENGTH_LONG).show();
                } else {

                    new Thread() {
                        @Override
                        public void run() {
                            int num = init(name, pwd);
                            if (num > 0) {
                                Looper.prepare();
                                Toast.makeText(ReviserPasswordActivity.this, "修改密码成功", Toast.LENGTH_LONG).show();

                                Intent it = new Intent(ReviserPasswordActivity.this, MainActivity.class);
                                Log.i(TAG, "run: " + it);
                                it.putExtra("user_id", data);
                                startActivity(it);
                                Looper.loop();
                                finish();

                            }
                        }
                    }.start();
                }
            }
        });
    }

    private int init(String name, String pwd) {
        String urlPath="http://192.168.56.1:8080/WorkingAssistant/changePassword.action";
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
                Log.i(TAG, "init: "+user);
                id=1;

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
