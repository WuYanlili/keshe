package com.example.lucky.keshe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.Plan;
import com.example.lucky.keshe.data.bean.User;

import java.util.List;

/**
 * Created by lucky on 2018/9/10.
 */

public class HistoryPlanAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private List<Plan> mPlans;
    int data;
    public HistoryPlanAdapter(Context context,List<Plan> planList,int m){
        mInflater=LayoutInflater.from(context);
        mPlans=planList;
        data=m;
    }

    @Override
    public int getCount() {
        return mPlans.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
       ViewHolder holder = null;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.history_plan_item, parent, false); //加载布局
            holder = new ViewHolder();
            holder.planName=convertView.findViewById(R.id.history_plan_item);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Plan plan=mPlans.get(position);
        holder.planName.setText(plan.getName());
        return convertView;
    }
    class ViewHolder{
        TextView planName;
    }
}
