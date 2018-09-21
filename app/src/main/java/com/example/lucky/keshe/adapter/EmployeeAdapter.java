package com.example.lucky.keshe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucky.keshe.NetUtils;
import com.example.lucky.keshe.R;
import com.example.lucky.keshe.activity.ManageCheckPlanActivity;
import com.example.lucky.keshe.activity.ViewActivity;
import com.example.lucky.keshe.data.bean.User;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by lucky on 2018/9/18.
 */

public class EmployeeAdapter extends BaseAdapter implements View.OnClickListener{
    private static final String TAG = "EmployeeAdapter";
    private LayoutInflater mInflater;
    private List<User> mUsers;
    private Context context;
    private int data;
    @Override
    public int getCount() {
        return mUsers.size();
    }
    public EmployeeAdapter(Context context,List<User> users,int s){
        mInflater=LayoutInflater.from(context);
        mUsers=users;
        data=s;
    }

    @Override
    public Object getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.plan_item, parent, false); //加载布局
            holder = new ViewHolder();
            holder.planName=convertView.findViewById(R.id.plan_item);
            //holder.check=convertView.findViewById(R.id.bt_check);
            holder.delete=convertView.findViewById(R.id.bt_delete);
            //holder.delete.setTag(R.id.delete,position);
            holder.delete.setOnClickListener(this);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
            //holder.check.setTag(R.id.check,position);
            //holder.check.setOnClickListener(this);

        }
        User user=mUsers.get(position);
        holder.planName.setText(user.getId().toString());
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.bt_delete:
                Log.i(TAG, "onClick: "+"delete");
                new Thread(){
                    @Override
                    public void run() {
                        init(data);

                    }
                }.start();
                Log.i(TAG, "onClick: ");
                break;
        }
    }

    private void init(int data) {
        String urlPath="http://192.168.56.1:8080/WorkingAssistant/deleteUser.action";
        URL url;
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("user_id",data);  //参数put到json串里


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
                Log.i(TAG, "init: delete"+user);

            }else{
                Looper.prepare();
                Log.i(TAG, "init: deletefail");
                Toast.makeText (context,"数据提交失败", Toast.LENGTH_LONG ).show();
                Looper.loop();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ViewHolder{
        TextView planName;
        //Button check;
        Button delete;
    }
}
