package com.example.lucky.keshe.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lucky.keshe.PieChart;
import com.example.lucky.keshe.R;
import com.example.lucky.keshe.data.bean.MonthBean;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucky on 2018/9/15.
 */

public class PieFragment extends Fragment implements OnChartValueSelectedListener{
    private PieChart mChart;
    private MonthBean mData;
    private TextView tvDes;
    private static  final  String DATA_KEY="pie_fragment_data_key";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments=getArguments();
        if (arguments!=null){
            mData=arguments.getParcelable(DATA_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate=inflater.inflate(R.layout.fragment_pie,null);
        mChart=inflate.findViewById(R.id.pc_chart);
        tvDes = inflate.findViewById(R.id.tv_des);
        initView();
        return inflate;
    }

    private void initView() {
        setData();
        mChart.setCenterText(getCenterText());
        mChart.getData().getDataSet().setDrawValues(true);
        mChart.getLegend().setEnabled(true);
        mChart.setDescription(null);
        mChart.setHoleRadius(55f);
        mChart.setRotationEnabled(true);
        mChart.setOnChartValueSelectedListener(this);
    }
    private CharSequence getCenterText() {
        CharSequence centerText = "总计划\n" + mData.getSum() + "个";
        SpannableString spannableString = new SpannableString(centerText);
        spannableString.setSpan(new ForegroundColorSpan(Color.rgb(178, 178,178)), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(64, true), 3, centerText.length()-1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }


    private void setData() {
        List<String> titles = new ArrayList<>();
        List<PieEntry> entrys = new ArrayList<>();
        for (int i = 0; i < mData.obj.size(); i++) {
            MonthBean.PieBean pieBean = mData.obj.get(i);
            titles.add(pieBean.title);
            entrys.add(new PieEntry(pieBean.getValue(),pieBean.getTitle()));
        }
        PieDataSet dataSet = new PieDataSet(entrys, "");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(23, 213, 159));
        colors.add(Color.rgb(245, 166, 35));
        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(22);
        mChart.setData(pieData);
        mChart.invalidate();
    }

    public static PieFragment newInstance(MonthBean data) {
        Bundle args = new Bundle();
        args.putParcelable(DATA_KEY,data);
        PieFragment fragment = new PieFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float proportion = 360f/mData.getSum();
        //float angle = 90-mData.obj.get(e.getXIndex()).value*proportion/2-mData.getSum(e.getXIndex())*proportion;
        //mChart.setRotationAngle(angle);
        //updateDesText(e.getXIndex());
    }
    private void updateDesText(int index) {
        tvDes.setText(mData.obj.get(index).title + ": " + mData.obj.get(index).value);
    }
    @Override
    public void onNothingSelected() {

    }
}
