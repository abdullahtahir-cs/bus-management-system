package com.example.smdfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientMainActivity extends AppCompatActivity {

    Button viewbuses,terminal,organizeatrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
    }

    public void ViewBuses(View view) {



    }



    public void OrganizeATrip(View view) {
        Intent intent=new Intent(ClientMainActivity.this,Triporganizer.class);
        startActivity(intent);

    }


    public void ViewTerminal(View view) {
        Intent intent=new Intent(ClientMainActivity.this,Triporganizer.class);
        startActivity(intent);

    }
}