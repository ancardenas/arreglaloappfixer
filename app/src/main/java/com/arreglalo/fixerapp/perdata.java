package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class perdata extends AppCompatActivity {
    private EditText ciudad,numero,correo,contrasena,confirmCont;
    private Fixer fixer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdata);
        fixer = new Fixer();
        ciudad = findViewById(R.id.ed_ciudadreg);
        numero = findViewById(R.id.ed_numeroreg);
        correo = findViewById(R.id.ed_correoreg);
        contrasena = findViewById(R.id.ed_contrasenareg);
        confirmCont = findViewById(R.id.ed_confirmContreg);


    }

    public void click(View view){

        if (contrasena.getText().toString().equals(confirmCont.getText().toString())){
            fixer.setCiudad(ciudad.getText().toString());
            fixer.setTelefono(numero.getText().toString());
            fixer.setCorreo(correo.getText().toString());
            fixer.setContrasena(contrasena.getText().toString());
            Intent intent= new Intent(this,fixInfo.class);
            intent.putExtra("fixer",fixer);
            startActivity(intent);
        }else {
            Toast.makeText(this,"Las dos contraseñas no son identícas, intente de nuevo",Toast.LENGTH_SHORT).show();

        }


    }


}