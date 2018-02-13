package com.aboxs.template_android.api;

import com.aboxs.template_android.model.PilihKotaModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Abox's on 10/02/2018.
 */

public interface AboxApi {
    String TOKEN_JADWAL_SHOLAT = "7c656d25166c3dc0188afbf0f620ba37";
    String SERVER_JADWAL_SHOLAT = "http://wahidganteng.ga/";

    String PATH_SELECT_KOTA = "process/api/"+TOKEN_JADWAL_SHOLAT+"/jadwal-sholat/get-kota";


    @Headers("Content-Type:application/json")
    @GET(PATH_SELECT_KOTA)
    Call<PilihKotaModel> pilih_kota();
}
