package com.example.smdfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class BPMS_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpms_main);
    }

    public void AdminModule(View view) {
        Intent intent = new Intent(BPMS_MainActivity.this, FragmentChanging.class);
        intent.putExtra("key", "admin");
        startActivity(intent);
    }


    public void ClientModule(View view) {
        Intent intent = new Intent(BPMS_MainActivity.this, FragmentChanging.class);
        intent.putExtra("key", "client");
        startActivity(intent);
    }
}