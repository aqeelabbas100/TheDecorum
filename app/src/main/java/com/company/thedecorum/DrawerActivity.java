package com.company.thedecorum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.company.thedecorum.Fargments.HomeFragment;
import com.company.thedecorum.Fargments.LampFragment;
import com.company.thedecorum.Fargments.RecommendationFragment;
import com.company.thedecorum.Fargments.TableFragment;
import com.company.thedecorum.Fargments.VaseFragment;
import com.google.android.material.navigation.NavigationView;

public class DrawerActivity extends AppCompatActivity{
/*
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

       // toolbar = findViewById(R.id.toolBar);
       // setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragments(new HomeFragment());
    }



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.nav_home){
            showFragments(new HomeFragment());
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if(id == R.id.nav_recommendation){
            showFragments(new RecommendationFragment());
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if(id == R.id.nav_lamp){
            showFragments(new LampFragment());
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if(id == R.id.nav_vase){
            showFragments(new VaseFragment());
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if(id == R.id.nav_table){
            showFragments(new TableFragment());
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return false;
    }

    private void showFragments(Fragment fragment){

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, fragment);
        ft.commit();
    }

    public void onBackPressed(){

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }*/
}