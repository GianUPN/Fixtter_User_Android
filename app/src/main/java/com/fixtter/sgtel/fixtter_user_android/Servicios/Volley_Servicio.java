package com.fixtter.sgtel.fixtter_user_android.Servicios;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Giancarlo on 04/09/2017.
 */

public class Volley_Servicio {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CONSUMERKEY = "ck_cfaf32e212c68c7c66b9a8f43625af72";
    private static final String CONSUMERSECRET = "cs_814f422a33bc737d188cb6a05ea4897c";
    private static final String METHOD = "HMAC-SHA1";
    private static final String URL = "http://192.168.1.21/wordpress/wc-api/v2/orders";
    private static final String PARAMNAME_KEY = "oauth_consumer_key";
    private static final String PARAMNAME_SECRET = "oauth_consumer_secret";
    private static final String PARAMNAME_NONCE = "oauth_nonce";
    private static final String PARAMNAME_TIMESTAMP = "oauth_timestamp";
    private static final String PARAMNAME_SIGNATURE = "oauth_signature";
    private static final String PARAMNAME_SIGNATURE_METHOD = "oauth_signature_method";

    public String CLAVE_CLIENTE = "ck_0b6e946fd3720e02a6b865c287bac197ddbd56ed";
    public String CLAVE_SECRET = "cs_31fbd5b8a41b9eb34ea24b94005ab4833a128393";
    public String url = "https://www.fixtter.com.pe/wp-json/wc/v2/products?consumer_key=" + CLAVE_CLIENTE + "&consumer_secret=" + CLAVE_SECRET;
    private RequestQueue requestQueue;
    public Context context;

    public Volley_Servicio(Context context){
        this.context = context;
    }

    public void GET_CUSTOMERS(){
    // Crear nueva cola de peticiones
        nuke();
        requestQueue= Volley.newRequestQueue(context);

    // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response) {
                            // display response

                            Log.d("Response", response.toString());
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //xd
                            Log.d("Error.Response", "");
                        }
                    }
            );

    // add it to the RequestQueue
            requestQueue.add(getRequest);
            requestQueue.start();

        }

    public static void nuke() {
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
