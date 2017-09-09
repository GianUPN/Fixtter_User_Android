package com.fixtter.sgtel.fixtter_user_android;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.List;


/**
 * Created by GIANCARLO on 05/01/2017.
 */

public class Adapter_Categorias extends RecyclerView.Adapter<Adapter_Categorias.ListaViewHolder> {

    private List<Categorias> items;
    public FragmentManager fragmentManager;
    public static class ListaViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item

        public Button btn_card;

        public ListaViewHolder(View v) {
            super(v);
            btn_card = (Button) v.findViewById(R.id.btn_card);
        }
    }

    public Adapter_Categorias(List<Categorias> items, FragmentManager fragmentManager) {
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
    public void onBindViewHolder(ListaViewHolder viewHolder, int i) {
        //AQUI VAN TODOS LOS ELEMENTOS DE LA LISTA.
        viewHolder.btn_card.setText(items.get(i).getNombre());

    }
}