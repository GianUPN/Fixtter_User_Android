package com.fixtter.sgtel.fixtter_user_android.Interfaces;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class Fragment_WEB_Reservar_Fixtter extends Fragment {

    WebView webview_reserva;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_WEB_Reservar_Fixtter() {
        // Required empty public constructor
    }
    public static Fragment_WEB_Reservar_Fixtter newInstance(String param1, String param2) {
        Fragment_WEB_Reservar_Fixtter fragment = new Fragment_WEB_Reservar_Fixtter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__web__reservar__fixtter, container, false);
        try {
            Volley_Servicio voley = new Volley_Servicio(getContext());
            voley.nuke();
            webview_reserva = (WebView) view.findViewById(R.id.webview_reserva);
            WebSettings webSettings = webview_reserva.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webview_reserva.getSettings().setUseWideViewPort(true);
            webview_reserva.getSettings().setLoadWithOverviewMode(true);
            webview_reserva.getSettings().setJavaScriptEnabled(true);
            webview_reserva.clearSslPreferences();
            webview_reserva.getSettings().setDomStorageEnabled(true);
            Map<String,String> map = new HashMap<>();
            map.put("user","danycalderonp");
            if (Build.VERSION.SDK_INT >= 21) {
                webview_reserva.getSettings().setMixedContentMode( WebSettings.MIXED_CONTENT_ALWAYS_ALLOW );
            }
            webview_reserva.loadUrl("http://www.fixtter.com.pe/producto/carlos-pulido-chaparro/",map);
            webview_reserva.setWebViewClient(new MyWebViewClient());

        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            return false;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            super.onReceivedSslError(view, handler, error);

            // this will ignore the Ssl error and will go forward to your site
            String message = "SSL Certificate error.";
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_NOTYETVALID:
                    message = "The certificate is not yet valid.";
                    break;
            }
            System.out.println(message);
            handler.proceed();
            //view.loadUrl("https://www.fixtter.com.pe/producto/carlos-pulido-chaparro/");
        }
    }

}
