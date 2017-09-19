package com.fixtter.sgtel.fixtter_user_android.Interfaces;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.fixtter.sgtel.fixtter_user_android.Controladores.Dao_Products;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.Volley_Servicio;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Conocer_Fixtter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ID = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String id_fixtter;
    private String mParam2;
    public TextView txt_nombre,txt_descripcion,txt_precio;
    public RatingBar ratingBar;
    public ImageSwitcher img_fixtter;

    public Fragment_Conocer_Fixtter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Conocer_Fixtter.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Conocer_Fixtter newInstance(String param1, String param2) {
        Fragment_Conocer_Fixtter fragment = new Fragment_Conocer_Fixtter();
        Bundle args = new Bundle();
        args.putString(ID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_fixtter = getArguments().getString(ID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__conocer__fixtter, container, false);
        txt_nombre = (TextView)view.findViewById(R.id.txt_nombre);
        txt_precio = (TextView)view.findViewById(R.id.txt_precio);
        txt_descripcion = (TextView)view.findViewById(R.id.txt_descripcion);
        ratingBar = (RatingBar)view.findViewById(R.id.rating_fixtter);
        img_fixtter = (ImageSwitcher) view.findViewById(R.id.img_fixtter);
        cargar_datos();
        return view;
    }
    public  void cargar_datos(){
        Dao_Products dao = new Dao_Products(getContext());
        dao.Get_elemento(id_fixtter, "", new Volley_Servicio.VolleyResponseListener() {
            @Override
            public void onError(VolleyError message) {
                if (message instanceof NoConnectionError) {
                    Snackbar.make(getView(), "Conectando", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    cargar_datos();
                } else if (message instanceof TimeoutError) {
                    Snackbar.make(getView(), "Exceso de tiempo de espera", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(getView(), "Error de conexión", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onResponse(Object response) {
                try {
                    JSONObject object = (JSONObject) response;
                    txt_nombre.setText(object.getString("name"));
                    txt_precio.setText("Precio: $/"+object.getString("price"));
                    ratingBar.setRating((float) object.getDouble("average_rating"));
                    txt_descripcion.setText(object.getString("description"));
                    JSONArray imagearray = object.getJSONArray("images");
                    final List<String> imagelist = new ArrayList<String>();
                    for (int j = 0; j < imagearray.length(); j++) {
                        JSONObject imageObject = (JSONObject) imagearray.get(j);
                        imagelist.add(imageObject.getString("src"));
                    }
                    img_fixtter.setFactory(new ViewSwitcher.ViewFactory() {
                        @Override
                        public View makeView() {
                            ImageView imageView = new ImageView(getContext());
                            Picasso.with(getContext())
                                    .load(imagelist.get(0))
                                    .resize(500, 500)
                                    .centerCrop()
                                    .into(imageView);
                            return imageView;
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
