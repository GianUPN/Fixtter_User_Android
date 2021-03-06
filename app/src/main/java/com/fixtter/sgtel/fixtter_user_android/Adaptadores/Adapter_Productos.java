package com.fixtter.sgtel.fixtter_user_android.Adaptadores;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fixtter.sgtel.fixtter_user_android.Entidades.Categorias;
import com.fixtter.sgtel.fixtter_user_android.Entidades.Producto;
import com.fixtter.sgtel.fixtter_user_android.Interfaces.Fragment_Conocer_Fixtter;
import com.fixtter.sgtel.fixtter_user_android.Interfaces.Fragment_Elegir_Fixtter;
import com.fixtter.sgtel.fixtter_user_android.Interfaces.Fragment_Reservar_Fixtter;
import com.fixtter.sgtel.fixtter_user_android.Interfaces.Fragment_WEB_Reservar_Fixtter;
import com.fixtter.sgtel.fixtter_user_android.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Giancarlo on 13/09/2017.
 */

public class Adapter_Productos extends RecyclerView.Adapter<Adapter_Productos.ListaViewHolder> {
    private List<Producto> items;
    public FragmentManager fragmentManager;
    public  Context context;
    public static class ListaViewHolder extends RecyclerView.ViewHolder {

        public Button btn_comprar;
        public Button btn_info;
        public TextView txt_nombre,txt_descripcion;
        public RatingBar ratingBar;
        public ImageView img_fixtter;

        public ListaViewHolder(View v) {
            super(v);

            txt_nombre = (TextView)v.findViewById(R.id.txt_nombre);
            txt_descripcion = (TextView)v.findViewById(R.id.txt_descripcion);
            ratingBar = (RatingBar)v.findViewById(R.id.rating_fixtter);
            img_fixtter = (ImageView)v.findViewById(R.id.img_fixtter);
            img_fixtter = (ImageView)v.findViewById(R.id.img_fixtter);
            btn_comprar = (Button) v.findViewById(R.id.btn_reservar);
            btn_info = (Button)v.findViewById(R.id.btn_masinfo);
        }
    }

    public Adapter_Productos(List<Producto> items, FragmentManager fragmentManager, Context context) {
        this.items = items;
        this.fragmentManager = fragmentManager;
        this.context =context;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public Adapter_Productos.ListaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_elegir_fixtter, viewGroup, false);
        return new Adapter_Productos.ListaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Adapter_Productos.ListaViewHolder viewHolder, final int i) {
        //AQUI VAN TODOS LOS ELEMENTOS DE LA LISTA.
        viewHolder.txt_nombre.setText(items.get(i).getNombre());
        viewHolder.txt_descripcion.setText("Precio: " + items.get(i).getPrecio());
        viewHolder.ratingBar.setRating((float) items.get(i).getRating());
        final String image = items.get(i).getImage_collection().get(0);
        Picasso.with(context)
                .load(image)
                .resize(150, 150)
                .centerCrop()
                .into(viewHolder.img_fixtter);
    viewHolder.btn_comprar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = Fragment_WEB_Reservar_Fixtter.newInstance("",
                    "");
            Bundle bundle = fragment.getArguments();
            bundle.putInt("ID",items.get(i).getId());
            bundle.putString("NOMBRE",items.get(i).getNombre());
            bundle.putString("IMAGEN",image);
            fragment.setArguments(bundle);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_content, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    });
        viewHolder.btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = Fragment_Conocer_Fixtter.newInstance(String.valueOf(items.get(i).getId()),
                        String.valueOf(items.get(i).getId()));
                Bundle bundle = fragment.getArguments();
                bundle.putString("ID", String.valueOf(items.get(i).getId()));
                fragment.setArguments(bundle);
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }
}
