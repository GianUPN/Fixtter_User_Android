package com.fixtter.sgtel.fixtter_user_android.Interfaces;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Clientes;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.AppPreferences;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class Activity_Login extends AppCompatActivity {
    LoginButton loginButton;
    Button btn_correo;
    EditText txt_correo, txt_pass;
    CallbackManager callbackManager;
    Dao_Clientes dao_clientes;
    private GraphRequest request;
    private GraphRequest.GraphJSONObjectCallback graphRequest = new GraphRequest.GraphJSONObjectCallback() {
        @Override
        public void onCompleted(final JSONObject object, GraphResponse response) {
            try {
                final String email = object.getString("email");
                final String nombre = object.getString("first_name");
                final String apellido = object.getString("last_name");
                btn_correo.setEnabled(false);

                Dao_Clientes dao_clientes = new Dao_Clientes(getApplicationContext());
                String propiedades = "email=" + email;
                dao_clientes.Get_lista_filtro(propiedades, "",
                new Volley_Servicio.VolleyResponseListener() {
                    @Override
                    public void onError(VolleyError message) {
                        System.out.println(message.toString());
                        if(message instanceof NoConnectionError){
                            Snackbar.make(getCurrentFocus(), "Conectando", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            request.executeAsync();
                        } else
                        if(message instanceof TimeoutError){
                            Snackbar.make(getCurrentFocus(), "Exceso de tiempo de espera", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            btn_correo.setEnabled(true);
                        }
                    }

                    @Override
                    public void onResponse(Object response) {
                        JSONArray jsonArray = (JSONArray) response;
                        if(jsonArray.length()>0) {
                            Snackbar.make(getCurrentFocus(), "Usuario ya existe", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            System.out.println(response.toString());
                            try {
                                JSONObject json = (JSONObject) jsonArray.get(0);
                                Intent intent = new Intent(getApplicationContext(), Navegacion_Principal.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                AppPreferences appPreferences = new AppPreferences(getApplicationContext());
                                appPreferences.saveUserEmail(json.getString("email"));
                                appPreferences.saveUserName(json.getString("first_name"));
                                appPreferences.saveUserLastName(json.getString("last_name"));
                                startActivity(intent);
                            }catch (Exception e){e.printStackTrace();}
                        }else{
                            Snackbar.make(getCurrentFocus(), "Nuevo Usuario", Snackbar.LENGTH_LONG)
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
        setContentView(R.layout.activity__login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dao_clientes = new Dao_Clientes(getApplicationContext());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_correo = (Button)findViewById(R.id.btn_correo);
        txt_correo = (EditText)findViewById(R.id.txt_correo);
        txt_pass = (EditText) findViewById(R.id.txt_pass);
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
            public void onClick(final View v) {
                //Intent intent = new Intent(getApplicationContext(), Activity_CrearUsuario.class);
                //startActivity(intent);
                if (validar_campos()) {

                    String[] user = txt_correo.getText().toString().split("@");
                    final String propiedades = "username=" + user[0] + "&email=" + txt_correo.getText().toString() + "&password=" + txt_pass.getText().toString();
                    btn_correo.setEnabled(false);
                    dao_clientes.Login_password(propiedades, "", new Volley_Servicio.VolleyResponseListener() {
                        @Override
                        public void onError(VolleyError message) {
                            System.out.println(message.toString());
                            if (message instanceof NoConnectionError) {
                                Snackbar.make(v, "Conectando", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                btn_correo.callOnClick();
                            } else if (message instanceof TimeoutError) {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Exceso de tiempo de espera", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                btn_correo.setEnabled(true);
                            } else {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Correo y/o password incorrectos", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                btn_correo.setEnabled(true);
                            }
                        }

                        @Override
                        public void onResponse(Object response) {
                            JSONObject json = (JSONObject) response;
                            if (json.has("token")) {
                                Snackbar.make(v, "Login correcto", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                System.out.println(response.toString());
                                try {
                                    String filtro = "email="+json.getString("user_email");
                                    ingresar_sistema(filtro);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Correo y/o password incorrectos", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                System.out.println(response.toString());

                            }
                        }
                    });
                }else{
                    Snackbar.make(v, "Valide los datos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void ingresar_sistema(final String filtro){
        dao_clientes.Get_lista_filtro(filtro, "", new Volley_Servicio.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                System.out.println(message.toString());
                if (message instanceof NoConnectionError) {
                    Snackbar.make(getCurrentFocus(), "Conectando", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    ingresar_sistema(filtro);
                } else if (message instanceof TimeoutError) {
                    Snackbar.make(getCurrentFocus(), "Exceso de tiempo de espera", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    btn_correo.setEnabled(true);
                } else {
                    btn_correo.setEnabled(true);
                }
            }
            @Override
            public void onResponse(Object response) {
                JSONArray jsonArray = (JSONArray) response;
                try{
                    JSONObject json = (JSONObject) jsonArray.get(0);
                    Intent intent = new Intent(getApplicationContext(), Navegacion_Principal.class);
                    AppPreferences appPreferences = new AppPreferences(getApplicationContext());
                    appPreferences.saveUserEmail(json.getString("email"));
                    appPreferences.saveUserName(json.getString("first_name"));
                    appPreferences.saveUserLastName(json.getString("last_name"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
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

    public boolean validar_campos(){
        if(txt_pass.getText().toString().equals("")){
            return false;
        }
        if(txt_correo.getText().toString().equals("")){
            return false;
        }
        if(!txt_correo.getText().toString().contains("@")){
            return false;
        }
        return true;
    }
}
