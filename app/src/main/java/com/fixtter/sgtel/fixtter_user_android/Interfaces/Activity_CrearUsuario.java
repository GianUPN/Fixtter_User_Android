package com.fixtter.sgtel.fixtter_user_android.Interfaces;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Clientes;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.AppPreferences;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_CrearUsuario extends AppCompatActivity {

    Button btn_registrar;
    EditText txt_nombre,txt_apellido,txt_email,txt_pass,txt_cell;
    AppCompatSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__crear_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_registrar = (Button)findViewById(R.id.btn_registrar);
        txt_nombre = (EditText)findViewById(R.id.txt_nombre);
        txt_apellido = (EditText)findViewById(R.id.txt_apellido);
        txt_email = (EditText)findViewById(R.id.txt_email);
        txt_pass = (EditText)findViewById(R.id.txt_pass);
        txt_cell = (EditText)findViewById(R.id.txt_cell);
        spinner = (AppCompatSpinner) findViewById(R.id.spinner1);
        try{
            txt_nombre.setText(getIntent().getStringExtra("nombre"));
            txt_apellido.setText(getIntent().getStringExtra("apellido"));
            txt_email.setText(getIntent().getStringExtra("email"));
        }catch (Exception e){
            e.printStackTrace();
        }
        List<String> paises = new ArrayList<String>();
        paises.add("Perú");
        paises.add("Colombia");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paises);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
        listeners();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void listeners(){
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verificar_campos()) {
                    try {
                        final Dao_Clientes servicio = new Dao_Clientes(getApplicationContext());
                        final Map<String, String> map = new HashMap<>();
                        map.put("email", String.valueOf(txt_email.getText()));
                        map.put("first_name", String.valueOf(txt_nombre.getText()));
                        map.put("last_name", String.valueOf(txt_apellido.getText()));
                        map.put("password", String.valueOf(txt_pass.getText()));
                        String email = "email=" + txt_email.getText().toString();
                        servicio.Get_elemento_propiedades(email, "", new Volley_Servicio.VolleyResponseListener() {
                            @Override
                            public void onError(VolleyError message) {
                                Log.d("ERROR", message.toString());
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Error de conexion", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }

                            @Override
                            public void onResponse(Object response) {
                                JSONArray jsonArray = (JSONArray) response;
                                if (jsonArray.length() > 0) {
                                    Snackbar.make(getWindow().getDecorView().getRootView(), "Ya existe el usuario", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }else{
                                    servicio.Set_elemento(map, "", new Volley_Servicio.VolleyResponseListener() {
                                        @Override
                                        public void onError(VolleyError message) {
                                            Log.d("ERROR", message.toString());
                                            Snackbar.make(getWindow().getDecorView().getRootView(), "Error de conexion", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }

                                        @Override
                                        public void onResponse(Object response) {
                                            Log.d("CORRECTO", response.toString());
                                            try {
                                                JSONArray jsonArray = (JSONArray) response;
                                                JSONObject json = (JSONObject) jsonArray.get(0);
                                                Intent intent = new Intent(getApplicationContext(), Navegacion_Principal.class);
                                                AppPreferences appPreferences = new AppPreferences(getApplicationContext());
                                                appPreferences.saveUserEmail(json.getString("email"));
                                                appPreferences.saveUserName(json.getString("first_name"));
                                                appPreferences.saveUserLastName(json.getString("last_name"));
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            }catch (Exception e){e.printStackTrace();}
                                        }
                                    });
                                }
                            }
                        });

                    }catch(Exception e){e.printStackTrace();}
                }else{
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Debe llenar todos los campos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    public  boolean verificar_campos(){
        if(txt_nombre.getText().toString().equals(""))return false;
        if(txt_apellido.getText().toString().equals(""))return false;
        if(txt_cell.getText().toString().equals(""))return false;
        if(txt_email.getText().toString().equals(""))return false;
        if(txt_pass.getText().toString().equals(""))return false;
        return true;
    }
}
