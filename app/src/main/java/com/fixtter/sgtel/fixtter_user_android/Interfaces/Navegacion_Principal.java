package com.fixtter.sgtel.fixtter_user_android.Interfaces;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fixtter.sgtel.fixtter_user_android.R;
import com.fixtter.sgtel.fixtter_user_android.Servicios.AppPreferences;

public class Navegacion_Principal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RelativeLayout relativeLayout;
    FloatingActionButton fab;
    Toolbar toolbar;
    NavigationView navigationView;
    TextView txt_nombre,txt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegacion__principal);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.main_content);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        listeners();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        View hView = navigationView.getHeaderView(0);
        txt_nombre = (TextView)hView.findViewById(R.id.text_nombre);
        txt_email = (TextView)hView.findViewById(R.id.text_correo);

        AppPreferences appPreferences = new AppPreferences(getApplicationContext());
        txt_nombre.setText(appPreferences.getUserName()+" "+appPreferences.getUserLastName());
        txt_email.setText(appPreferences.getUserEmail());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    public void listeners(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Button btn_pedir = (Button)findViewById(R.id.btn_pedir);
        btn_pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.setVisibility(View.INVISIBLE);
                Fragment fragment = Fragment_Menu_Categorias.newInstance("", "");
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .add(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
                toolbar.setTitle(getString(R.string.Nav_Categorias));
                //fragments.add(fragment);
            }
        });
        Button btn_pedidos = (Button)findViewById(R.id.btn_pedidos);
        btn_pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 1) {
                Log.i("Navegacion Principal", "popping backstack");
                fm.popBackStack();
            } else {
                Log.i("Navegacion Principal", "nothing on backstack, calling super");
                fab.setVisibility(View.VISIBLE);
                super.onBackPressed();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegacion__principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            // Handle the camera action
        } else if (id == R.id.nav_editar) {

        } else if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_pais) {
            fab.setVisibility(View.INVISIBLE);
            Fragment fragment = Fragment_Cambiar_Pais.newInstance("", "");
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_content, fragment)
                    .addToBackStack(null)
                    .commit();
            //toolbar.setTitle(getString(R.string.Nav_Categorias));

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
