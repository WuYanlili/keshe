package com.example.lucky.keshe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.Plan;

import java.util.List;

/**
 * Created by lucky on 2018/9/20.
 */

public class ManageHistoryPlanAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Plan> mPlans;
    int data;

    public ManageHistoryPlanAdapter(Context context, List<Plan> planList,int i){
        mInflater=LayoutInflater.from(context);
        mPlans=planList;
        data=i;
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
