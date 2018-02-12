package com.aboxs.template_android.activity;

import android.support.v7.app.AppCompatActivity;

import com.aboxs.template_android.api.AboxApi;
import com.aboxs.template_android.util.AboxApps;

/**
 * Created by Abox's on 10/02/2018.
 */

public class AboxActivity extends AppCompatActivity {
    public AboxApi getAPI() {
        return AboxApps.getAPI();
    }
}
