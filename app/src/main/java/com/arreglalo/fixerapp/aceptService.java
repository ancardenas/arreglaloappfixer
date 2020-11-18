package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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

public class aceptService extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    private Solicitud solicitud;
    private Fixer fixer;
    private TextView accSer,detalles,hora,fecha;
    private Button acept;
    private Spinner spinner;

    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acept_service);
        solicitud = (Solicitud) getIntent().getSerializableExtra("solicitud");
        fixer = (Fixer) getIntent().getSerializableExtra("fixer");
        acept = findViewById(R.id.bt_acept);
        accSer = findViewById(R.id.txtacSer);
        detalles = findViewById(R.id.txt_acepDet);
        hora = findViewById(R.id.txt_acepHour);
        fecha = findViewById(R.id.txt_acepDate);

        accSer.setText(solicitud.getService());
        detalles.setText(solicitud.getDetails());
        hora.setText(solicitud.getHora()+":"+solicitud.getMinuto());
        fecha.setText(solicitud.getDia()+"/"+solicitud.getMes());
        spinner = findViewById(R.id.spinner);
        queue = Volley.newRequestQueue(this);
        String [] opciones =  {"1","2","3","4","5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);
        //cargarWebService();


    }

    public void  click (View view){

        String seleccion = spinner.getSelectedItem().toString();
         solicitud.setDuracion(Integer.parseInt(seleccion));
        Toast.makeText(this,seleccion,Toast.LENGTH_SHORT).show();

        cargarWebService();

    }
    private void cargarWebService() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("CARGAAAA");
        dialog.show();
        String url = "https://arreglalo.co/actualizarAcepted.php?id="+solicitud.getId()+
                "&idF="+fixer.getId()+
                "&duracion="+solicitud.getDuracion()+
                "&precio="+solicitud.getDuracion();
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        url=url.replace(" ","%20");
        //String url1 ="http://192.168.0.10/arreglalo/index.php?nombre=yo&numero=2344&direccion=yo&correo=yo&ciudad=yo&contrasena=yo&calificacion=5&id=5";

        queue.add(jsonObjectRequest);


    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.hide();
        Toast.makeText(this,"MAMA NO LO LOGRE",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(this,"MAMA LO LOGRE",Toast.LENGTH_SHORT).show();
        dialog.hide();
        finish();

    }
}