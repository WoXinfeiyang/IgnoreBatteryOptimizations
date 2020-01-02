package com.fxj.ignorebatteryoptimizations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button btnAutoLaunch;
    private Button btnStartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAutoLaunch = findViewById(R.id.btn_auto_launch);
        btnAutoLaunch.setOnClickListener(this);

        btnStartService = findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(this);

        boolean stateIgnoringBatteryOptimizations=BatteryUtils.isIgnoringBatteryOptimizations(MainActivity.this);
        Log.d("fxj","##MainActivity.onCreate##stateIgnoringBatteryOptimizations="+stateIgnoringBatteryOptimizations);
        if(stateIgnoringBatteryOptimizations){
//            BatteryUtils.requestIgnoreBatteryOptimizations(MainActivity.this);
            BatteryUtils.jumpAutoStartManagementActivity(MainActivity.this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("fxj","##MainActivity.onPause##");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("fxj","##MainActivity.onStop##");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("fxj","##MainActivity.onDestroy##");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_auto_launch:
                BatteryUtils.jumpAutoStartManagementActivity(MainActivity.this);
                break;

            case R.id.btn_start_service:
                Intent intent=new Intent(MainActivity.this,BackgroundService.class);
                startService(intent);
                break;
        }
    }
}
