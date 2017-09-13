package com.fixtter.sgtel.fixtter_user_android.Controladores;

import android.content.Context;

import com.fixtter.sgtel.fixtter_user_android.Servicios.Codigos;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import java.util.Map;

/**
 * Created by Giancarlo on 09/09/2017.
 */

public class Dao_Categorias extends Volley_Servicio{
    public Dao_Categorias(Context context) {
        super(context);
    }
    @Override
    public void Get_lista_all(String a,Volley_Servicio.VolleyResponseListener request) {
        super.Get_lista_all( Codigos.GET_CATEGORIA_ALL,request);
    }

    @Override
    public void Get_elemento_filtro(String propiedades, String a, Volley_Servicio.VolleyResponseListener request) {
        super.Get_elemento_filtro(propiedades, Codigos.GET_CATEGORIA_PROPIEDADES,request);
    }

    @Override
    public void Set_elemento(Map<String,String> map, String a, Volley_Servicio.VolleyResponseListener request) {
        super.Set_elemento(map, Codigos.SET_CATEGORIA,request);
    }
}
