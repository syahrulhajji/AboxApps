package com.aboxs.template_android.fragment;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aboxs.template_android.R;
import com.aboxs.template_android.activity.MainActivity;
import com.aboxs.template_android.adapter.AdapterHome;
import com.aboxs.template_android.adapter.AdapterKota;
import com.aboxs.template_android.database.SharePreference;
import com.aboxs.template_android.model.HomeModel;
import com.aboxs.template_android.model.PilihKotaModel;
import com.aboxs.template_android.util.Utility;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.aboxs.template_android.util.AboxApps.getAPI;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends AboxFragment {
    SharePreference sharePreference;
    Utility utility;
    private AdapterKota adapterKota;
    AlertDialog alertDialogKota;
    @BindView(R.id.tv_select_your_city)TextView tvSelectYourCity;
    LayoutInflater inflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        sharePreference = new SharePreference(getContext());
        utility = new Utility();
        ((MainActivity)getActivity()).showMenuSlide();

        return view;
    }

    @OnClick(R.id.tv_select_your_city)
    void selectCity(){
        final Call<PilihKotaModel> pilihKotaModelCall = getAPI().pilih_kota();
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please Wait..");
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                pilihKotaModelCall.cancel();
            }
        });
        pd.show();
        pilihKotaModelCall.enqueue(new Callback<PilihKotaModel>() {
            @Override
            public void onResponse(Call<PilihKotaModel> call, Response<PilihKotaModel> response) {

                if(response.body().getStatus().equals("sukses")){
                    inflater  = getActivity().getLayoutInflater();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    View viewStatus = inflater.inflate(R.layout.dialog_choice,null);
                    builder.setView(viewStatus);
                    alertDialogKota = builder.create();
                    alertDialogKota.show();
                    final RecyclerView rvStatus = (RecyclerView)viewStatus.findViewById(R.id.rv_choice);
                    TextView tvTitle = (TextView)viewStatus.findViewById(R.id.tv_title);
                    tvTitle.setText("Select Your City");

                    adapterKota = new AdapterKota(response.body().getDataKota(),tvSelectYourCity);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rvStatus.setLayoutManager(layoutManager);
                    rvStatus.setItemAnimator(new DefaultItemAnimator());
                    rvStatus.setAdapter(adapterKota);

                }else {
                    Toast.makeText(getContext(),response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PilihKotaModel> call, Throwable t) {
                Toast.makeText(getContext(),"Server Is Down",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected String getTitle() {
        return getString(R.string.home);
    }
}
