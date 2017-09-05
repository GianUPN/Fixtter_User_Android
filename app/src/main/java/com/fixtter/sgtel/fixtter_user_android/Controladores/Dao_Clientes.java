package com.fixtter.sgtel.fixtter_user_android.Controladores;

import android.content.Context;

import com.fixtter.sgtel.fixtter_user_android.Servicios.Codigos;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import java.util.Map;

/**
 * Created by Giancarlo on 05/09/2017.
 */

public class Dao_Clientes extends Volley_Servicio{
    public Dao_Clientes(Context context) {
        super(context);
    }
    @Override
    public void Get_lista_pagina(int pagina,String a,VolleyResponseListener request) {
        super.Get_lista_pagina(pagina, Codigos.GET_CLIENTES_PAGE,request);
    }

    @Override
    public void Set_elemento(Map<String,String> map, String a, VolleyResponseListener request) {
        super.Set_elemento(map, Codigos.SET_CLIENTES,request);
    }
}
