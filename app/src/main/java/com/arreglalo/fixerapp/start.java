package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }
    public void regist(View view){
        Intent intent = new Intent(this,perdata.class);
        startActivity(intent);
    }
    public void iniSesion(View view){
        Intent intent = new Intent(this,initSesion.class);
        startActivity(intent);
    }
}