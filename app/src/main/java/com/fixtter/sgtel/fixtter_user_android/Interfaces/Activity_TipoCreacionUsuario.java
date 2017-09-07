package com.fixtter.sgtel.fixtter_user_android.Interfaces;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.android.volley.ExecutorDelivery;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Clientes;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.AppPreferences;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

public class Activity_TipoCreacionUsuario extends AppCompatActivity {

    LoginButton loginButton;
    Button btn_correo;
    CallbackManager callbackManager;
    private GraphRequest request;
    private GraphRequest.GraphJSONObjectCallback graphRequest = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(final JSONObject object, GraphResponse response) {
            try {
                final String email = object.getString("email");
                final String nombre = object.getString("first_name");
                final String apellido = object.getString("last_name");

                Dao_Clientes dao_clientes = new Dao_Clientes(getApplicationContext());
                String propiedades = "email=" + email;
                dao_clientes.Get_elemento_propiedades(propiedades, "", new Volley_Servicio.VolleyResponseListener() {
                    @Override
                    public void onError(VolleyError message) {
                        System.out.println(message.toString());
                        if(message instanceof NoConnectionError){
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Error de conexion", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            request.executeAsync();
                        } else
                        if(message instanceof TimeoutError){
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Exceso de tiempo de espera", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onResponse(Object response) {
                        JSONArray jsonArray = (JSONArray) response;
                        if(jsonArray.length()>0) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Usuario ya existe", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            System.out.println(response.toString());
                            try {
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
                        }else{
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Nuevo Usuario", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            System.out.println(response.toString());
                            Intent intent = new Intent(getApplicationContext(), Activity_CrearUsuario.class);
                            intent.putExtra("email", email);
                            intent.putExtra("nombre", nombre);
                            intent.putExtra("apellido", apellido);
                            startActivity(intent);

                        }
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity__tipo_creacion_usuario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_correo = (Button) findViewById(R.id.btn_correo);
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_location"));
        // Other app specific specialization
        callbackManager = CallbackManager.Factory.create();
        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            request = GraphRequest.newMeRequest(token.getCurrentAccessToken(),graphRequest);
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,email,first_name,last_name,location");
            request.setParameters(parameters);
            request.executeAsync();
        }

        listeners();
    }
    public void listeners(){
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i("INFO", "token " + loginResult.getAccessToken().getToken());
                System.out.println(loginResult.toString());
                request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),graphRequest);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,first_name,last_name,location");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        btn_correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_CrearUsuario.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}