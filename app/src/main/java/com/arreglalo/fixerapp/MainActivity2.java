package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
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
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private TextView tipo;
    private TextView detalles;
    private Solicitud solicitud;
    private Fixer fixer;

    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;

    private ArrayList<Solicitud> solicituds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tipo = findViewById(R.id.txt_tipo);
        detalles=findViewById(R.id.txt_detalles);
        fixer = (Fixer) getIntent().getSerializableExtra("fixer");


        solicituds = new ArrayList<>();



        queue = Volley.newRequestQueue(this);
        cargarWebService();
        cargarLista();
        /*
        Existe problema con la carga, Como hacer que el servicio espere la carga
        para continuar ???
         */


    }
    public void cargarLista(){
        ListAdapter adapter = new ListAdapter(solicituds,this);
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.solicitud_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), solicituds.get(recyclerView.getChildAdapterPosition(v)).getService(), Toast.LENGTH_SHORT).show();
            solicitud = solicituds.get(recyclerView.getChildAdapterPosition(v));
            Intent intent=new Intent(getApplicationContext(),aceptService.class);
            intent.putExtra("fixer",(Serializable) fixer);
            intent.putExtra("solicitud",(Serializable) solicitud);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
    public void clokc(View view) {

        cargarWebService();
        cargarLista();

    }
    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
       String url = "https://arreglalo.000webhostapp.com/listaSol.php";
       dialog.show();

       jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
       queue.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
    dialog.hide();
    Toast.makeText(this,"No fue posible encontrar servicios",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray jsonArray = response.optJSONArray("solicitud");
        solicituds = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = null;
                Solicitud solicitud = new Solicitud();
                jsonObject = jsonArray.getJSONObject(i);
                solicitud.setService(jsonObject.optString("Nom_S"));
                solicitud.setDetails(jsonObject.optString("Desc_S"));
                solicitud.setId(jsonObject.optInt("Id_S"));
                solicitud.setAcepted(jsonObject.optBoolean("Acepted"));

                solicituds.add(solicitud);
                //Toast.makeText(this,solicitud.getService()+i,Toast.LENGTH_SHORT).show();
            }
            dialog.hide();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }}