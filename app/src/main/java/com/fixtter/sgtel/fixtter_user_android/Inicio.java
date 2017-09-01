package com.fixtter.sgtel.fixtter_user_android;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {

    Button btn_llamada,btn_login,btn_crearcuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        btn_llamada = (Button) findViewById(R.id.btn_llamada);
        btn_crearcuenta = (Button) findViewById(R.id.btn_crearcuenta);
        btn_login = (Button) findViewById(R.id.btn_login);
        listeners();
    }
    public void listeners(){
        btn_llamada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = "+51993936066";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        btn_crearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_TipoCreacionUsuario.class);
                startActivity(intent);
            }
        });

    }
}
