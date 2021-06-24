package com.example.fyp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LampActivity extends AppCompatActivity implements LampAdapter.AbcOnClickListener {

    private RecyclerView view;
    private LampAdapter adapter;
    private ArrayList<Lamp> list;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);

        view = findViewById(R.id.recycleview);
        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("Lamp");
        view.addItemDecoration(new DividerItemDecoration(
                view.getContext(),DividerItemDecoration.VERTICAL
        ));
        view.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new LampAdapter(this,list);
        view.setAdapter(adapter);
        adapter.setabcOnClickListener(this);
        reference.addListenerForSingleValueEvent(listener);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Lamp p = snapshot.getValue(Lamp.class);
                    list.add(p);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(LampActivity.this,"No record found",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(LampActivity.this,databaseError.toString(),Toast.LENGTH_LONG).show();
        }
    };


    public void clickme(int pos) {
        Toast.makeText(this,pos + "" , Toast.LENGTH_SHORT).show();
    }


    public void viewdetail(int pos) {
        Lamp p = list.get(pos);
        Toast.makeText(this,p.size +" "+ p.name,Toast.LENGTH_SHORT).show();
    }


    public void ar(int pos) {
        Lamp p = list.get(pos);

        Intent intent = new Intent(this,ArActivity.class);
        intent.putExtra("id", String.valueOf(pos));
        startActivity(intent);
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
/*
    @Override
    public void clickme(int pos) {

    }

    @Override
    public void viewdetail(int pos) {

    }

    @Override
    public void arlamp(int pos) {

    }*/
}