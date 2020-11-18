package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class initSesion extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private Fixer fixer;

    private EditText correo, contrasena;

    private boolean correcto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_sesion);
        correo = findViewById(R.id.ed_correoIn);
        contrasena = findViewById(R.id.ed_contrasenaIn);
        fixer = new Fixer();
        queue = Volley.newRequestQueue(this);

    }
    public void click(View view){
        cargarWebService();


    }





    public void cargarWebService(){
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        String url = "https://arreglalo.co/initSesionFix.php?correo="+correo.getText().toString() +
                "&contrasena="+contrasena.getText().toString();
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

        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject= null;
        try {
            jsonObject  =jsonArray.getJSONObject(0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject!=null){
            correcto=true;

        }else{
            correcto=false;

        }if (correcto){
            //Toast.makeText(getApplicationContext(),"SI ES USUARIO",Toast.LENGTH_SHORT).show();
            fixer.setId(jsonObject.optInt("Id_F"));
            fixer.setCiudad(jsonObject.optString("City_F"));
            fixer.setCalificacion(jsonObject.optDouble("Calif_F"));
            fixer.setTelefono(jsonObject.optString("Tel_F"));
            fixer.setCorreo(jsonObject.optString("Mail_F"));
            fixer.setNombre(jsonObject.optString("Nom_F"));
            fixer.setContrasena(jsonObject.optString("Cont_F"));
            Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
            intent.putExtra("fixer",(Serializable) fixer);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Su usuario o contraseña no son correctos" ,Toast.LENGTH_SHORT).show();
        }


    }

}