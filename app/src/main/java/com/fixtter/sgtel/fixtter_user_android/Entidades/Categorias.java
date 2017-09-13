package com.fixtter.sgtel.fixtter_user_android.Entidades;

/**
 * Created by Giancarlo on 09/09/2017.
 */

public class Categorias {
    private int id;
    private String nombre;
    private String descripcion;
    private String image_src;

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
}
