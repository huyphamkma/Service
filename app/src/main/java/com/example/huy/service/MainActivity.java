package com.example.huy.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_Start;
    private Button button_Stop;
    private boolean isConnected;
    private BoundService boundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_Start = findViewById(R.id.button_Start);
        button_Stop = findViewById(R.id.button_Stop);
        button_Start.setOnClickListener(this);
        button_Stop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, BoundService.class);
        switch (v.getId()){
            case R.id.button_Start:
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.button_Stop:
                if(isConnected){
                    unbindService(serviceConnection);
                    isConnected = false;
                }
                break;
            default:
                break;
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isConnected = true;
            BoundService.BoundExample binder = (BoundService.BoundExample) service;
            boundService = binder.getService();
            Toast.makeText(MainActivity.this, boundService.getCurrentTime(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isConnected = false;
        }
    };
}
