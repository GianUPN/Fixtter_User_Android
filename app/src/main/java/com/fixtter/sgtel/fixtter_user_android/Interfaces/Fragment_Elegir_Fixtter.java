package com.fixtter.sgtel.fixtter_user_android.Interfaces;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.fixtter.sgtel.fixtter_user_android.Adaptadores.Adapter_Categorias;
import com.fixtter.sgtel.fixtter_user_android.Adaptadores.Adapter_Productos;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Categorias;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Products;
import com.fixtter.sgtel.fixtter_user_android.Entidades.Categorias;
import com.fixtter.sgtel.fixtter_user_android.Entidades.Producto;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Elegir_Fixtter extends Fragment {
    // TODO: PARAMETROS Y VARIABLES
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CATEGORIA = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    RecyclerView recycler_productos;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private int pagina = 1;
    List<Producto> productosList;
    public Fragment_Elegir_Fixtter() {
        // Required empty public constructor
    }

    /**
     *      ELEGIR LOS FIXTTERS(PRODUCTOS) POR CATEGORIA
     *
     */
    public static Fragment_Elegir_Fixtter newInstance(String categoria, String param2) {
        Fragment_Elegir_Fixtter fragment = new Fragment_Elegir_Fixtter();
        Bundle args = new Bundle();
        args.putString(CATEGORIA, categoria);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(CATEGORIA);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__elegir__fixtter, container, false);
        recycler_productos = (RecyclerView) view.findViewById(R.id.recycler_fixtters);
        productosList = new ArrayList<>();
        lManager = new LinearLayoutManager(getContext());

        recycler_productos.setLayoutManager(lManager);
        cargar_fixttersPorCategoria();

        return view;
    }
    public void cargar_fixttersPorCategoria(){
        Dao_Products dao_products = new Dao_Products(getContext());
        mParam1 = mParam1.replace(" ","%20");//CUIDAR ESPACIOS EN BLANCO
        dao_products.Get_lista_pagina_filtro_V1(pagina,"filter[product_cat]="+mParam1,"", new Volley_Servicio.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                System.out.println(message.toString());
                if(message instanceof NoConnectionError){
                    Snackbar.make(getView(), "Error de conexion", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    cargar_fixttersPorCategoria();
                }
            }
            @Override
            public void onResponse(Object response) {
                System.out.println(response.toString());
                try{
                    JSONArray jsonArray = (JSONArray) response;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Producto producto = new Producto();
                        producto.setId(jsonObject.getInt("id"));
                        producto.setNombre(jsonObject.getString("name"));
                        producto.setRating(jsonObject.getDouble("average_rating"));
                        producto.setPrecio(jsonObject.getDouble("price"));
                        try {
                            JSONArray imagearray = jsonObject.getJSONArray("images");
                            List<String> imagelist = new ArrayList<String>();
                            for (int j = 0; j < imagearray.length(); j++) {
                                JSONObject imageObject = (JSONObject) imagearray.get(j);
                                imagelist.add(imageObject.getString("src"));
                            }
                            producto.setImage_collection(imagelist);
                            productosList.add(producto);
                        }catch (Exception e){e.printStackTrace();}
                    }
                    adapter = new Adapter_Productos(productosList,getFragmentManager(),getContext());
                    recycler_productos.setAdapter(adapter);
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }
    // TODO: LISTENERS Y METODOS

}
