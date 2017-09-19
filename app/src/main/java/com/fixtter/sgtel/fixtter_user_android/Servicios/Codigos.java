package com.fixtter.sgtel.fixtter_user_android.Servicios;

/**
 * Created by Giancarlo on 05/09/2017.
 */

public class Codigos {

    /* API SERVICE PARAMS ACTIONS*/

    /* PRODUCTOS */
    public static String GET_PRODUCTOS_ID = "wc/v2/products/";
    public static String GET_PRODUCTOS_ALL = "wc/v2/products?";
    public static String GET_PRODUCTOS_PAGE = "wc/v2/products?page=";
    public static String GET_PRODUCTOS_PAGE_V1 = "wc/v1/products?page=";
    public static String SET_PRODUCTOS = "wc/v2/products?";

    /* CLIENTES */
    public static String GET_CLIENTES_PROPIEDADES = "wc/v2/customers?";
    public static String GET_CLIENTES_ID = "wc/v2/customers/";
    public static String GET_CLIENTES_ALL = "wc/v2/customers?";
    public static String GET_CLIENTES_PAGE = "wc/v2/customers?page=";
    public static String SET_CLIENTES = "wc/v2/customers?";

    /* CATEGORIAS */
    public static String GET_CATEGORIA_PROPIEDADES = "wc/v2/products/categories?";
    public static String GET_CATEGORIA_ID = "wc/v2/products/categories/";
    public static String GET_CATEGORIA_ALL = "wc/v2/products/categories?per_page=50&";
    public static String GET_CATEGORIA_ALL_V1 = "wc/v1/products/categories?per_page=50&";
    public static String GET_CATEGORIA_PAGE = "wc/v2/products/categories?page=";
    public static String SET_CATEGORIA = "wc/v2/products/categories?";

    public static String LOGIN_PASSWORD = "jwt-auth/v1/token?";

}
