package com.fixtter.sgtel.fixtter_user_android.Adaptadores;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fixtter.sgtel.fixtter_user_android.Entidades.Categorias;
import com.fixtter.sgtel.fixtter_user_android.Interfaces.Fragment_Elegir_Fixtter;
import com.fixtter.sgtel.fixtter_user_android.Interfaces.Fragment_Menu_Categorias;
import com.fixtter.sgtel.fixtter_user_android.R;

import java.util.List;


/**
 * Created by GIANCARLO on 05/01/2017.
 */

public class Adapter_Categorias extends RecyclerView.Adapter<Adapter_Categorias.ListaViewHolder> {

    private List<Categorias> items;
    public FragmentManager fragmentManager;
    public Context context;
    public static class ListaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        public AppCompatButton btn_card;

        public ListaViewHolder(View v) {
            super(v);
            btn_card = (AppCompatButton) v.findViewById(R.id.btn_card);
        }
    }

    public Adapter_Categorias(List<Categorias> items, FragmentManager fragmentManager, Context context) {
        this.context = context;
        this.items = items;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_categorias, viewGroup, false);
        return new ListaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListaViewHolder viewHolder, final int i) {
        //AQUI VAN TODOS LOS ELEMENTOS DE LA LISTA.
        tintViewDrawable(viewHolder.btn_card,i);
        viewHolder.btn_card.setText(items.get(i).getNombre());
        viewHolder.btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = Fragment_Elegir_Fixtter.newInstance(items.get(i).getNombre(),
                        items.get(i).getNombre());
                Bundle bundle = fragment.getArguments();
                fragment.setArguments(bundle);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
    private void tintViewDrawable(AppCompatButton view,int pos) {
        while(pos>5){
            pos=pos-6;
        }
        if(pos == 0)view.setSupportBackgroundTintList(context.getResources().getColorStateList(R.color.colorAccent));
        if(pos == 1)view.setSupportBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimary));
        if(pos == 2)view.setSupportBackgroundTintList(context.getResources().getColorStateList(R.color.colorPrimaryDark));
        if(pos == 3)view.setSupportBackgroundTintList(context.getResources().getColorStateList(R.color.colorOrange));
        if(pos == 4)view.setSupportBackgroundTintList(context.getResources().getColorStateList(R.color.colorGreen));
        if(pos == 5)view.setSupportBackgroundTintList(context.getResources().getColorStateList(R.color.colorRosa));
    }
}