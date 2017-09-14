package com.fixtter.sgtel.fixtter_user_android.Interfaces;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fixtter.sgtel.fixtter_user_android.R;


public class Fragment_Cambiar_Pais extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView img_peru,img_colombia;


    public Fragment_Cambiar_Pais() {
        // Required empty public constructor
    }

    /**
     * Fragment de cambio de paises.
     *
     *
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Cambiar_Pais newInstance(String param1, String param2) {
        Fragment_Cambiar_Pais fragment = new Fragment_Cambiar_Pais();
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
        View view = inflater.inflate(R.layout.fragment__cambiar__pais, container, false);
        img_peru = (ImageView)view.findViewById(R.id.img_peru);
        img_colombia = (ImageView)view.findViewById(R.id.img_colombia);
        img_peru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        img_colombia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

}
