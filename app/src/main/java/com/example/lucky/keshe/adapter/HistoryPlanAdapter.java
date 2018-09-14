package com.example.lucky.keshe.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.Plan;

import java.util.List;

/**
 * Created by lucky on 2018/9/10.
 */

public class HistoryPlanAdapter extends ArrayAdapter<Plan>{

    private int resourceID;

    public HistoryPlanAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<Plan> objects) {
        super(context, textViewResourceId, objects);
        resourceID=textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Plan plan=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        TextView planName=view.findViewById(R.id.plan_item);
        planName.setText(plan.getName());
        return view;
    }
}
