package com.arreglalo.fixerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class fixInfo extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    EditText nombre,apellido,fechanac,direccion;
    RadioButton hombre,mujer;
    CheckBox tyc,tdp;

    private Fixer fixer;
    private ProgressDialog dialog;
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_info);
        nombre = findViewById(R.id.ed_nombrereg);
        apellido = findViewById(R.id.ed_apellidosreg);
        fechanac = findViewById(R.id.ed_fechanacreg);
        direccion = findViewById(R.id.ed_direccionreg);
        hombre = findViewById(R.id.rb_hombre);
        mujer =findViewById(R.id.rb_mujer);
        tyc = findViewById(R.id.ck_tyc);
        tdp = findViewById(R.id.ck_tdp);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando");
        fixer = (Fixer) getIntent().getSerializableExtra("fixer");


        queue = Volley.newRequestQueue(this);
        String url1 = "https://arreglalo.co/recibirFix.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url1,null,this,this);
        queue.add(jsonObjectRequest);
        dialog.show();
    }
    public void click(View view){
        fixer.setNombre(nombre.getText().toString()+" "+apellido.getText().toString());
        fixer.setFecha(fechanac.getText().toString());
        fixer.setDireccion(direccion.getText().toString());
        if (hombre.isChecked()){
            fixer.setGenero("Hombre");
        }else if(mujer.isChecked()) {
            fixer.setGenero("Mujer");
        }else{
            fixer.setGenero("No registra");
        }
        cargarWebService();

    }

    private void cargarWebService() {

        dialog.show();
        String url = "https://arreglalo.co/insertFixer.php?" +
                "id="+fixer.getId()+
                "&ciudad="+fixer.getCiudad() +
                "&calificacion="+fixer.getCalificacion() +
                "&numero="+fixer.getTelefono() +
                "&correo="+fixer.getCorreo() +
                "&nombre="+fixer.getNombre()+
                "&contrasena="+fixer.getContrasena()+
                "&genero="+fixer.getGenero()+
                "&fecha="+fixer.getFecha()+
                "&direccion="+fixer.getDireccion();
        String url1 = "https://arreglalo.co/recibirFix.php";

        url=url.replace(" ","%20");
        //String url1 ="http://192.168.0.10/arreglalo/index.php?nombre=yo&numero=2344&direccion=yo&correo=yo&ciudad=yo&contrasena=yo&calificacion=5&id=5";


        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dialog.hide();
        Toast.makeText(this,"Error al conectar, intente de nuevo",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        String url1 = "https://arreglalo.co/recibirFix.php";
        //Toast.makeText(this,"MAMA LO LOGRE",Toast.LENGTH_SHORT).show();
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


        }else{

            Intent intent = new Intent(this,MainActivity2.class);
            intent.putExtra("fixer",fixer);
            startActivity(intent);
        }

    }
}