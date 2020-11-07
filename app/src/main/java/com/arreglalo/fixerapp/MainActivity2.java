package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity2 extends AppCompatActivity {
    private TextView tipo;
    private TextView detalles;
    private Solicitud solicitud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        /*
        AQUI SE CARGARIA LA SOLICITUD en el objento solicitud
        de la base del datos

        y con el metodo tipo.setText(solicitud.getTipo());
        y detalles.setText(solicitud.getDetalles());
         */

    }
}