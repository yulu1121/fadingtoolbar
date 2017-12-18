package com.anshi.filechoice.ui;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextClock;


import com.anshi.filechoice.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextClock mTextClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
        initView();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        registerReceiver(usBroadcastReceiver, filter);
    }

    BroadcastReceiver usBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

// TODO Auto-generated method stub
            String action = intent.getAction();
            switch (action){
                case Intent.ACTION_TIME_CHANGED:
                    getTextClock(mTextClock);
                    break;
            }

        }


    };
    private void initView() {
        findViewById(R.id.party_file).setOnClickListener(this);
        mTextClock = (TextClock) findViewById(R.id.text_clock);
        if (mTextClock.is24HourModeEnabled()){
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss EE", Locale.getDefault());
            String format1 = format.format(new Date());
            mTextClock.setFormat24Hour(format1);
        }else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 aa hh:mm:ss EE", Locale.getDefault());
            String format1 = format.format(new Date());
            mTextClock.setFormat12Hour(format1);
        }
        getTextClock(mTextClock);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.party_file:

                Intent intent = new Intent(this,FileActivity.class);
                //intent.putExtra("fileName",cFolder.getName()+ File.separator+"党务公开");
                startActivity(intent);
                break;
            case R.id.country_side_file:
                break;
            case R.id.finance_file:
                break;
            case R.id.other_file:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(usBroadcastReceiver);
        super.onDestroy();

    }

    private void getTextClock(final TextClock clock){
        clock.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (clock.is24HourModeEnabled()){

                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss EE", Locale.getDefault());
                    String format1 = format.format(new Date());
                    clock.setFormat24Hour(format1);
                }else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 aa hh:mm:ss EE", Locale.getDefault());
                    String format1 = format.format(new Date());
                    clock.setFormat12Hour(format1);
                }
                clock.post(this);
            }
        },1000);
    }
}

