package com.aboxs.template_android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aboxs.template_android.R;
import com.aboxs.template_android.model.PilihKotaModel;

import java.util.List;

/**
 * Created by KEBHANA on 2/13/2018.
 */

public class AdapterKota extends RecyclerView.Adapter<AdapterKota.MyViewHolder> {
    private List<PilihKotaModel.DataKota> dataKotaList;
    private TextView tvKota;
    public AdapterKota(List<PilihKotaModel.DataKota> dataKotas, TextView tvKota) {
        this.dataKotaList = dataKotas;
        this.tvKota = tvKota;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PilihKotaModel.DataKota taskModel = dataKotaList.get(position);
        holder.tvNama.setText(dataKotaList.get(position).getNamaKota());
        holder.tvCode.setText(dataKotaList.get(position).getIdKota());
        holder.ll_kota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvKota.setText(holder.tvNama.getText().toString());
                tvKota.setTag(holder.tvCode.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataKotaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNama, tvCode;
        public LinearLayout ll_kota;
        public MyViewHolder(View view) {
            super(view);
            tvNama = (TextView) view.findViewById(R.id.tv_name);
            tvCode = (TextView) view.findViewById(R.id.tv_code);
            tvCode = (TextView) view.findViewById(R.id.tv_code);
            ll_kota = (LinearLayout)view.findViewById(R.id.ll_status);
        }
    }
}
