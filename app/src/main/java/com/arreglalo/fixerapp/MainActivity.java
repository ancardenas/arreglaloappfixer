package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    private EditText ciudad,telefono,correo,nombre,contrasena;

    private Fixer fixer;
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fixer = new Fixer();
        dialog = new ProgressDialog(this);
        dialog.setMessage("CARGAAAA");
        dialog.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ciudad = findViewById(R.id.ed_ciuad);
        telefono=findViewById(R.id.ed_telefono);
        correo = findViewById(R.id.ed_correo);
        nombre=findViewById(R.id.ed_nombre);
        contrasena = findViewById(R.id.ed_contrasena);


        queue = Volley.newRequestQueue(this);
        String url1 = "https://arreglalo.000webhostapp.com/recibirFix.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,this,this);
        queue.add(jsonObjectRequest);
    }
    public void clock(View view) {

        fixer.setNombre(nombre.getText().toString());
        String a = telefono.getText().toString();
        fixer.setTelefono(a);
        fixer.setCalificacion(5);
        fixer.setCiudad(ciudad.getText().toString());
        fixer.setCorreo(correo.getText().toString());
        fixer.setContrasena(contrasena.getText().toString());
        cargarWebService();

        Intent intent = new Intent(this,MainActivity2.class);
        intent.putExtra("fixer",(Serializable)fixer );
        startActivity(intent);
    }
    //https://arreglalo.000webhostapp.com/insertFixer.php?id=2&ciudad=bogota&calificacion=5&numero=3322&correo=yoyo&nombre=ud&contrasena=yo
    private void cargarWebService() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("CARGAAAA");
        dialog.show();
        String url = "https://arreglalo.000webhostapp.com/insertFixer.php?id="+fixer.getId()+
                "&ciudad=bogota"+fixer.getCiudad() +
                "&calificacion="+fixer.getCalificacion() +
                "&numero="+fixer.getTelefono() +
                "&correo="+fixer.getCorreo() +
                "&nombre="+fixer.getNombre()+
                "&contrasena="+fixer.getContrasena();
        String url1 = "https://arreglalo.000webhostapp.com/recibirFix.php";

        url=url.replace(" ","%20");
        //String url1 ="http://192.168.0.10/arreglalo/index.php?nombre=yo&numero=2344&direccion=yo&correo=yo&ciudad=yo&contrasena=yo&calificacion=5&id=5";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.hide();
        Toast.makeText(this,"MAMA NO LO LOGRE",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        String url1 = "https://arreglalo.000webhostapp.com/recibirFix.php";
        Toast.makeText(this,"MAMA LO LOGRE",Toast.LENGTH_SHORT).show();
        dialog.hide();
        if(response.optJSONArray("usuario")!=null) {
            JSONArray jsonArray = response.optJSONArray("usuario");
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(0);
                fixer.setId((jsonObject.optInt("Id_F") + 1));
                Toast.makeText(getApplicationContext(), fixer.getId() + " ", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{}

    }
}