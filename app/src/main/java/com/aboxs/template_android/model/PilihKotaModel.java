package com.aboxs.template_android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by KEBHANA on 2/13/2018.
 */

public class PilihKotaModel {

    @SerializedName("status")
    @Expose
    private String Status;

    @SerializedName("msg")
    @Expose
    private String Message;

    @SerializedName("count")
    @Expose
    private String Count;

    @SerializedName("data")

    @Expose
    private List<DataKota> DataKota;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public List<DataKota> getDataKota() {
        return DataKota;
    }

    public void setDataKota(List<DataKota> dataKota) {
        DataKota = dataKota;
    }

    public class DataKota {

        @SerializedName("id")
        @Expose
        private String idKota;

        @SerializedName("nama_kota")
        @Expose
        private String namaKota;

        public String getIdKota() {
            return idKota;
        }

        public void setIdKota(String idKota) {
            this.idKota = idKota;
        }

        public String getNamaKota() {
            return namaKota;
        }

        public void setNamaKota(String namaKota) {
            this.namaKota = namaKota;
        }
    }
}
