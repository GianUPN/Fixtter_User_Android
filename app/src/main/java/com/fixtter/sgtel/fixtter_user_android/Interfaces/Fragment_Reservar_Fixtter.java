package com.fixtter.sgtel.fixtter_user_android.Interfaces;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.fixtter.sgtel.fixtter_user_android.R;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class Fragment_Reservar_Fixtter extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int id;
    private String imagen;
    private String nombre;

    private LinearLayout zona_detalle,zona_fecha,zona_hora;
    private Button btn_servicio,btn_hora,btn_fecha;
    private TextView txt_fecha, txt_hora;
    public TextView txt_nombre,txt_descripcion;
    public RatingBar ratingBar;
    public ImageView img_fixtter;


    public Fragment_Reservar_Fixtter() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Reservar_Fixtter.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Reservar_Fixtter newInstance(String param1, String param2) {
        Fragment_Reservar_Fixtter fragment = new Fragment_Reservar_Fixtter();
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
            imagen = getArguments().getString("IMAGEN");
            nombre = getArguments().getString("NOMBRE");
            id = getArguments().getInt("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__reservar__fixtter, container, false);
        txt_nombre = (TextView)view.findViewById(R.id.txt_nombre);
        txt_descripcion = (TextView)view.findViewById(R.id.txt_descripcion);
        ratingBar = (RatingBar)view.findViewById(R.id.rating_fixtter);
        img_fixtter = (ImageView)view.findViewById(R.id.img_fixtter);
        zona_detalle = (LinearLayout) view.findViewById(R.id.zona_detalleservicio);
        zona_fecha = (LinearLayout) view.findViewById(R.id.zona_fecha);
        zona_hora = (LinearLayout) view.findViewById(R.id.zona_hora);
        btn_fecha = (Button)view.findViewById(R.id.btn_fecha);
        btn_hora = (Button)view.findViewById(R.id.btn_hora);
        btn_servicio = (Button)view.findViewById(R.id.btn_servicio);
        txt_fecha = (TextView)view.findViewById(R.id.txt_fecha);
        txt_hora = (TextView)view.findViewById(R.id.txt_hora);

        txt_nombre.setText(nombre);
        Picasso.with(getContext())
                .load(imagen)
                .resize(150, 150)
                .centerCrop()
                .into(img_fixtter);

        btn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zona_hora.getVisibility()==View.GONE){
                    zona_hora.setVisibility(View.VISIBLE);
                }else{
                    zona_hora.setVisibility(View.GONE);
                }
            }
        });
        btn_servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zona_detalle.getVisibility()==View.GONE){
                    zona_detalle.setVisibility(View.VISIBLE);
                }else{
                    zona_detalle.setVisibility(View.GONE);
                }
            }
        });
        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(zona_fecha.getVisibility()==View.GONE){
                    zona_fecha.setVisibility(View.VISIBLE);
                }else{
                    zona_fecha.setVisibility(View.GONE);
                }
            }
        });

        txt_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        txt_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    }
                },Calendar.getInstance().get(Calendar.HOUR),
                        Calendar.getInstance().get(Calendar.MINUTE),true).show();
            }
        });

        return view;
    }

}
