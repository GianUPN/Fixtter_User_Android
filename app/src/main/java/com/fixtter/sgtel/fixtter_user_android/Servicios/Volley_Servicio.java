package com.fixtter.sgtel.fixtter_user_android.Servicios;

import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Giancarlo on 04/09/2017.
 */

public class Volley_Servicio{

    private static final String CLAVE_CLIENTE = "ck_0b6e946fd3720e02a6b865c287bac197ddbd56ed";
    private static final String CLAVE_SECRET = "cs_31fbd5b8a41b9eb34ea24b94005ab4833a128393";
    private static final String url = "https://www.fixtter.com.pe/wp-json/wc/v2/";
    private static final String TOKEN = "consumer_key=" + CLAVE_CLIENTE + "&consumer_secret=" + CLAVE_SECRET;
    private Context context;

    public Volley_Servicio(Context context){
        this.context=context;
    }

    public void Get_lista_pagina(int pagina,String tabla,final VolleyResponseListener request){
        try {
            //evitar SSL
            nuke();// Eliminar esto cuando se publique el servidor.
            // Crear nueva cola de peticiones
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = url + tabla + pagina + "&" + TOKEN;
            JsonArrayRequest getrequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // muestra response
                            Log.d("Response", response.toString());
                            request.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //muestra error
                            Log.d("Error.Response", error.toString());
                            request.onError(error);
                        }
                    }
            );
            // add it to the RequestQueue
            getrequest.setRetryPolicy(new DefaultRetryPolicy(90000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //time MS
            requestQueue.add(getrequest);
            requestQueue.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        }

    public void Get_elemento_propiedades(String parametros,String tabla,final VolleyResponseListener request){
        try {
            //evitar SSL
            nuke();// Eliminar esto cuando se publique el servidor.
            // Crear nueva cola de peticiones
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = url + tabla + parametros +"&" + TOKEN;
            JsonArrayRequest getrequest = new JsonArrayRequest(Request.Method.GET, URL, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // muestra response
                            Log.d("Response", response.toString());
                            request.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //muestra error
                            Log.d("Error.Response", error.toString());
                            request.onError(error);
                        }
                    }
            );
            // add it to the RequestQueue
            getrequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    10,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //time MS
            requestQueue.add(getrequest);
            requestQueue.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Set_elemento(final Map<String,String> map, String tabla, final VolleyResponseListener request){
        try {
            //evitar SSL
            nuke();// Eliminar esto cuando se publique el servidor.
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            String URL = url + tabla + TOKEN;
            StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Log.d("Response", response);
                            request.onResponse(response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error.Response", error.toString());
                            request.onError(error);
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                { return map;  }
            };
            postRequest.setRetryPolicy(new DefaultRetryPolicy(90000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(postRequest);
            requestQueue.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface VolleyResponseListener {
        void onError(VolleyError message);
        void onResponse(Object response);
    }


    private static void nuke() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}
