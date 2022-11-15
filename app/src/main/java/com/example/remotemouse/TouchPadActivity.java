package com.example.remotemouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TouchPadActivity extends AppCompatActivity {
    ClientThread clientThread;
    private static final String CLIENT_THREAD_BUNDLE_KEY = "clientThread";
    private static final String SENSITIVITY_KEY = "sensitivity";
    public static final String KEY_SERVER_IP = "serverIP";
    public static final String KEY_SERVER_PORT = "serverPort";
    int sensitivity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touchpad);

        MySurfaceView surfView = findViewById(R.id.surfView);

        if(savedInstanceState != null){
            clientThread = (ClientThread)savedInstanceState.getSerializable(CLIENT_THREAD_BUNDLE_KEY);
            sensitivity = savedInstanceState.getInt(SENSITIVITY_KEY);
            surfView.setSensitivity(sensitivity);
            if(clientThread != null){
                surfView.addClientThread(clientThread);
            }
        }
        else {
            Intent intent = this.getIntent();
            String serverIP = intent.getStringExtra(TouchPadActivity.KEY_SERVER_IP);
            String serverPort = intent.getStringExtra(TouchPadActivity.KEY_SERVER_PORT);
            if(!serverIP.isEmpty() && !serverPort.isEmpty())
            {
                clientThread = new ClientThread(serverIP, Integer.parseInt(serverPort), false);
                surfView.addClientThread(clientThread);
            }
        }

        findViewById(R.id.leftMouseButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    clientThread.update("leftClickDown");
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    clientThread.update("leftClickUp");
                }
                return true;
            }
        });

        findViewById(R.id.rightMouseButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    clientThread.update("rightClickDown");
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    clientThread.update("rightClickUp");
                }
                return true;
            }
        });

        findViewById(R.id.middleMouseButton).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    clientThread.update("middleClickDown");
                } else if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    clientThread.update("middleClickUp");
                }
                return true;
            }
        });

        findViewById(R.id.sensitivity).setOnClickListener(v -> {
            switch (sensitivity)
            {
                case 1:
                    sensitivity++;
                    surfView.setSensitivity(sensitivity);
                    Toast.makeText(getApplicationContext(), "Чувствительность = " + sensitivity, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    sensitivity++;
                    surfView.setSensitivity(sensitivity);
                    Toast.makeText(getApplicationContext(), "Чувствительность = " + sensitivity, Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    sensitivity = 1;
                    surfView.setSensitivity(sensitivity);
                    Toast.makeText(getApplicationContext(), "Чувствительность = " + sensitivity, Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clientThread.stopThread();
    }

    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putSerializable(CLIENT_THREAD_BUNDLE_KEY, clientThread);
        savedInstanceState.putInt(SENSITIVITY_KEY, sensitivity);
    }
}