package com.fixtter.sgtel.fixtter_user_android.Interfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Clientes;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import java.util.HashMap;
import java.util.Map;

public class Activity_CrearUsuario extends AppCompatActivity {

    Button btn_registrar;
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
                Dao_Clientes servicio = new Dao_Clientes(getApplicationContext());
                Map<String,String> map = new HashMap<>();
                map.put("email","danycalderonp@hotmail.com");
                map.put("first_name","Giancarlo");
                map.put("last_name","Calderon Pereda");
                map.put("password","abc123");
                servicio.Set_elemento(map, "", new Volley_Servicio.VolleyResponseListener() {
                    @Override
                    public void onError(VolleyError message) {
                        System.out.println(message.toString());
                    }

                    @Override
                    public void onResponse(Object response) {
                        System.out.println(response.toString());
                        Intent intent = new Intent(getApplicationContext(), Navegacion_Principal.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
