package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText id;
    private EditText nombre;
    private EditText telefono;
    private Fixer fixer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
    }
    public void clock(View view){
        fixer = new Fixer();
        int i = Integer.valueOf(id.getText().toString());
        fixer.setId(i);
        fixer.setNombre(nombre.getText().toString());
        long a = Long.parseLong(telefono.getText().toString());
        fixer.setTelefono(a);
    }
}