package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
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

public class completeInfo extends AppCompatActivity implements com.android.volley.Response.Listener<JSONObject>, Response.ErrorListener{
    private Solicitud solicitud;
    TextView direccion,tipo,detalles,fecha,hora;

    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_info);
        solicitud =(Solicitud) getIntent().getSerializableExtra("solicitud");
        direccion = findViewById(R.id.txt_dircectionInfo);
        tipo = findViewById(R.id.txt_infoServ);
        detalles = findViewById(R.id.txt_infoDet);
        fecha = findViewById(R.id.txt_infoDate);
        hora =findViewById(R.id.txt_infoHour);
        tipo.setText(solicitud.getService());
        detalles.setText(solicitud.getDetails());
        fecha.setText(solicitud.getDia()+"/"+solicitud.getMes());
        hora.setText(solicitud.getHora()+":"+solicitud.getMinuto());
        queue = Volley.newRequestQueue(this);
        cargarWebService();
    }


    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.co/recibirUsuario.php?id="+solicitud.getIdU();
        dialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        dialog.hide();
        Cliente cliente1 = new Cliente();
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        try {
            jsonObject  =jsonArray.getJSONObject(0);
            direccion.setText(jsonObject.optString("Dir_U"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}