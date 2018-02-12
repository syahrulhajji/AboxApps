package com.aboxs.template_android.activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aboxs.template_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashScreenActivity extends AboxActivity {
    private int progressStatus = 0;
    int progressBarValue = 0;
    Handler handler = new Handler();
    @BindView(R.id.tv_status) TextView tvStatus;
    @BindView(R.id.pb_loading)ProgressBar pbLoading;
    private boolean isHandlerFinish=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        progressStatus = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressBarValue < 100) {
                    progressBarValue++;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbLoading.setProgress(progressBarValue);
                            tvStatus.setText(progressBarValue +"/"+pbLoading.getMax());
                            if(progressBarValue==100){
                                goToMainActivity();
                            }
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    private void goToMainActivity() {
        Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
