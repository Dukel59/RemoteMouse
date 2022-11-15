package com.example.remotemouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class CreateConnectionActivity extends AppCompatActivity {
    Intent intent;
    EditText serverIP;
    EditText serverPort;
    public static final String KEY_INTENT = "intent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_connection);

        serverIP = findViewById(R.id.etIP);
        serverPort = findViewById(R.id.etPort);

        if(savedInstanceState != null){
            serverIP.setText(savedInstanceState.getString(TouchPadActivity.KEY_SERVER_IP));
            serverPort.setText(savedInstanceState.getString(TouchPadActivity.KEY_SERVER_PORT));
        }

        findViewById(R.id.btnConnect).setOnClickListener(v -> {
            if (!serverIP.getText().toString().isEmpty() && !serverPort.getText().toString().isEmpty()) {

                Intent intent  = new Intent(this, TouchPadActivity.class);
                intent.putExtra(TouchPadActivity.KEY_SERVER_IP, serverIP.getText().toString());
                intent.putExtra(TouchPadActivity.KEY_SERVER_PORT, serverPort.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Заполните поля IP и/или Port", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TouchPadActivity.KEY_SERVER_IP, serverIP.getText().toString());
        outState.putString(TouchPadActivity.KEY_SERVER_PORT, serverPort.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "Сессия была прервана", Toast.LENGTH_SHORT).show();
    }
}