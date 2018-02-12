package com.aboxs.template_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aboxs.template_android.R;
import com.aboxs.template_android.model.HomeModel;

import java.util.List;

/**
 * Created by Abox's on 11/02/2018.
 */

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MyViewHolder> {

    private List<HomeModel> homeModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvStatus, tvName, tvDescription, tvAmount;

        public MyViewHolder(View view) {
            super(view);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDescription = (TextView) view.findViewById(R.id.tv_description);
            tvAmount = (TextView) view.findViewById(R.id.tv_amount);
        }
    }


    public AdapterHome(List<HomeModel> homeModelList) {
        this.homeModelList = homeModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeModel homeModel = homeModelList.get(position);
        holder.tvStatus.setText(homeModel.getStatus());
        holder.tvName.setText(homeModel.getName());
        holder.tvDescription.setText(homeModel.getDescription());
        holder.tvAmount.setText(homeModel.getAmount());
    }

    @Override
    public int getItemCount() {
        return homeModelList.size();
    }
}
