package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.ArrayList;

public class aceptedServices extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener {
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;

    private ArrayList<Solicitud> solicituds;
    private Solicitud solicitud;
    private Fixer fixer;
    private ArrayList<Integer> id_s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acepted_services);
        id_s = new ArrayList<>();
        fixer = (Fixer) getIntent().getSerializableExtra("fixer");
        //Toast.makeText(this,fixer.getId()+" ",Toast.LENGTH_SHORT).show();
        queue = Volley.newRequestQueue(this);
        cargarWebService();

    }


    public void cargarLista(){
        ListAdapter adapter = new ListAdapter(solicituds,this);
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.fix_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnClickListener(v -> {
           // Toast.makeText(getApplicationContext(), solicituds.get(recyclerView.getChildAdapterPosition(v)).getService(), Toast.LENGTH_SHORT).show();
            if(solicituds.get(recyclerView.getChildAdapterPosition(v)).isComplete()){
                Solicitud solicitud = solicituds.get(recyclerView.getChildAdapterPosition(v));
                Intent intent =  new Intent(this,completeInfo.class);
                intent.putExtra("solicitud",solicitud);
                startActivity(intent);
            }else {
                Toast.makeText(this,"El cliente esta revisando su cotización",Toast.LENGTH_SHORT).show();
            }


        });
        recyclerView.setAdapter(adapter);
        //Toast.makeText(this,"Estoy mostrando la lista ",Toast.LENGTH_SHORT).show();
    }
    public void clokc(View view) {

        cargarWebService();
        cargarLista();

    }
    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url1 ="https://arreglalo.co/fixList.php?id="+fixer.getId();
        String url = "https://arreglalo.co/listaSol1.php";
        dialog.show();

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,this,this);
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
        JSONArray jsonArray1 = response.optJSONArray("duracion");
        solicituds = new ArrayList<>();
        if(jsonObjectRequest.getUrl().equals("https://arreglalo.co/listaSol1.php")){

            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = null;
                    JSONObject jsonObject = null;
                    Solicitud solicitud = new Solicitud();
                    jsonObject = jsonArray.getJSONObject(i);
                    jsonObject1  =jsonArray1.getJSONObject(i);
                    solicitud.setService(jsonObject.optString("Nom_S"));
                    solicitud.setDetails(jsonObject.optString("Desc_S"));
                    solicitud.setId(jsonObject.optInt("Id_S"));
                    boolean acep;

                    if(jsonObject.optInt("Acepted")==1){
                        acep = true;
                    }else{
                        acep = false;
                    }
                    solicitud.setAcepted(acep);
                    boolean comp;

                    if(jsonObject.optInt("Complete")==1){
                        comp = true;
                    }else{
                        comp = false;
                    }
                    solicitud.setComplete(comp);
                    String fecha = jsonObject1.opt("Fecha") + " ";
                    solicitud.setAno(Integer.parseInt(fecha.substring(0,4)));
                    solicitud.setMes(Integer.parseInt(fecha.substring(5,7)));
                    solicitud.setDia(Integer.parseInt(fecha.substring(8,10)));
                    solicitud.setHora(Integer.parseInt(fecha.substring(11,13)));
                    solicitud.setMinuto(Integer.parseInt(fecha.substring(14,16)));
                    solicitud.setIdU(Integer.parseInt(jsonObject1.optString("Id_U1")));
                    if(id_s.contains(solicitud.getId())){
                        solicituds.add(solicitud);
                        //Toast.makeText(this,"Se añadio",Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(this,solicitud.getService()+i,Toast.LENGTH_SHORT).show();
                }
                dialog.hide();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cargarLista();




        }else{

            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = null;
                try {
                    jsonObject = jsonArray.getJSONObject(i);
                    id_s.add(jsonObject.optInt("Id_S2"));
                    //Toast.makeText(getApplicationContext(),id_s.get(i)+"EnTREEEE 2 ",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            String url = "https://arreglalo.co/listaSol1.php";
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            queue.add(jsonObjectRequest);
        }



    }
}