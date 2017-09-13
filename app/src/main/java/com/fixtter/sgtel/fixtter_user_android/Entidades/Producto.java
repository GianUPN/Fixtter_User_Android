package com.fixtter.sgtel.fixtter_user_android.Entidades;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Giancarlo on 13/09/2017.
 */

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private String image_src;
    private List<String> image_collection = new ArrayList<>();
    private double rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImage_src() {
        return image_src;
    }

    public void setImage_src(String image_src) {
        this.image_src = image_src;
    }

    public List<String> getImage_collection() {
        return image_collection;
    }

    public void setImage_collection(List<String> image_collection) {
        this.image_collection = image_collection;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
