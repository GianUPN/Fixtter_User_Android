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
import com.fixtter.sgtel.fixtter_user_android.Entidades.Categorias;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Categorias;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Menu_Categorias extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: VARIABLES Y PARAMETROS
    private String mParam1;
    private String mParam2;
    RecyclerView recycler_categorias;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    List<Categorias> categoriasList;


    public Fragment_Menu_Categorias() {
        // Required empty public constructor
    }

    /**
     * ELEGIR CATEGORIAS DE LOS FIXTTERS
     *
     *
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Menu_Categorias newInstance(String param1, String param2) {
        Fragment_Menu_Categorias fragment = new Fragment_Menu_Categorias();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_categorias, container, false);

        recycler_categorias = (RecyclerView)view.findViewById(R.id.recycler_categorias);
        categoriasList = new ArrayList<>();
        lManager = new LinearLayoutManager(getContext());
        recycler_categorias.setLayoutManager(lManager);
        cargar_categorias();
        return view;
    }
    public void cargar_categorias(){
        Dao_Categorias dao_categorias = new Dao_Categorias(getContext());
        dao_categorias.Get_lista_all("", new Volley_Servicio.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                System.out.println(message.toString());
                if(message instanceof NoConnectionError){
                    Snackbar.make(getView(), "Conectando", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    cargar_categorias();
                }
            }
            @Override
            public void onResponse(Object response) {
                System.out.println(response.toString());
                try{
                    JSONArray jsonArray = (JSONArray) response;
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Categorias categorias = new Categorias();
                        categorias.setId(jsonObject.getInt("id"));
                        categorias.setNombre(jsonObject.getString("name"));
                        categoriasList.add(categorias);
                    }
                    adapter = new Adapter_Categorias(categoriasList,getFragmentManager(),getContext());
                    recycler_categorias.setAdapter(adapter);
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

}
