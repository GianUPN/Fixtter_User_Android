package com.fixtter.sgtel.fixtter_user_android.Controladores;

import android.content.Context;

import com.fixtter.sgtel.fixtter_user_android.Servicios.Codigos;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import java.util.Map;


/**
 * Created by Giancarlo on 04/09/2017.
 */

public class Dao_Products extends Volley_Servicio{

    public Dao_Products(Context context) {
        super(context);
    }

    @Override
    public void Get_lista_pagina(int pagina,String a,VolleyResponseListener request) {
         super.Get_lista_pagina(pagina, Codigos.GET_PRODUCTOS_PAGE,request);
    }

    @Override
    public void Get_lista_pagina_filtro_V1(int pagina,String parametros,String tabla,VolleyResponseListener request) {
        super.Get_lista_pagina_filtro_V1(pagina,parametros,Codigos.GET_PRODUCTOS_PAGE,request);
    }

    @Override
    public void Set_elemento(Map<String,String> map,String a,VolleyResponseListener request) {
        super.Set_elemento(map, Codigos.SET_PRODUCTOS,request);
    }

}
    /*Map<String, String> params = new HashMap<String, String>();
                    params.put("name", "Alif");
                            params.put("domain", "http://itsalif.info");*/