package com.raki.cociocompany2.myapp2;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<HSDataProvider> mDataset;
    DecimalFormat decFor = new DecimalFormat(",##0.0");

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextName,mTextScore;
        public ViewHolder(View v){
            super(v);
            mTextName = (TextView)v.findViewById(R.id.hsName);
            mTextScore = (TextView)v.findViewById(R.id.hsScore);
        }
    }

    public MyAdapter(List<HSDataProvider> myDataSet){
        this.mDataset = myDataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HSDataProvider eventDataProvider = mDataset.get(position);
        String s = decFor.format(eventDataProvider.getHSScore());
        holder.mTextName.setText(eventDataProvider.getHSName());
        holder.mTextScore.setText(s);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
