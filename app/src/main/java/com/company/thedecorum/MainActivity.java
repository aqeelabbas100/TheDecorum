package com.company.thedecorum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Filter;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.company.thedecorum.Fargments.HomeFragment;
import com.company.thedecorum.Fargments.LampFragment;
import com.company.thedecorum.Fargments.RecommendationFragment;
import com.company.thedecorum.Fargments.TableFragment;
import com.company.thedecorum.Fargments.VaseFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.core.view.MenuItemCompat.getActionView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView view;

    private ProductAdapter adapter;
    private ArrayList<Product> list;
    private DatabaseReference reference;
    SharedPreferences mSharedPref;
    Query referenceSorted;



    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
         setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();



        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new ProductAdapter(this,list);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchData(s);
                Log.e("query 1",query.toString());
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
//        return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.mSort) {
            showSortDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void showSortDialog() {
        String[] sortOptions = {"Price: Low To High","Price: High To Low"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this) ;
        builder.setTitle("Sort By")
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(i==0)
                        {
                            /*SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort","Price: Low To High");
                            editor.apply();*/

                            view.setAdapter(adapter);
                            referenceSorted = FirebaseDatabase.getInstance().getReference().child("product").orderByChild("price");

                            Intent intent = new Intent(getApplicationContext(), HomeFragment.class);
                            intent.putExtra("id", String.valueOf(referenceSorted));

                        }
                        else if(i==1)
                        {
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort","Price: High To Low");
                            editor.apply();

                            view.setAdapter(adapter);
                        }



                    }
                });
        builder.create().show();
    }

    public void onBackPressed(){

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }
}