package com.example.fyp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.accessibilityservice.GestureDescription;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity   {

    private RecyclerView view;
    private ProductAdapter adapter;
    private ArrayList<Product> list;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = findViewById(R.id.recycleview);
        list = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference().child("product");
        view.addItemDecoration(new DividerItemDecoration(
                view.getContext(),DividerItemDecoration.VERTICAL
        ));
        view.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new ProductAdapter(this,list);
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
                    Product p = snapshot.getValue(Product.class);
                    list.add(p);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(MainActivity.this,"No record found",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(MainActivity.this,databaseError.toString(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void clickme(int pos) {
        Toast.makeText(this,pos + "" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void viewdetail(int pos) {
        Product p = list.get(pos);
        Toast.makeText(this,p.size +" "+ p.name,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ar(int pos) {
        Product p = list.get(pos);

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

//    private void searchData(String s){
//        DatabaseReference.collection()
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.action_settings){
            Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
            return true;
        }
        if(item.getItemId() == R.id.mSort) {
            //showSortDialog();
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
                            SharedPreferences.Editor editor = mSharedPref.edit();
                            editor.putString("Sort","Price: Low To High");
                            editor.apply();

                            view.setAdapter(adapter);

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


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.searchmenu,menu);
//
//        MenuItem item = menu.findItem(R.id.search);
//
//        SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processSearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processSearch(s);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }



//    public void processSearch(String s){
//        FirebaseRecyclerOptions<Product> options =
//                new FirebaseRecyclerOptions.Builder<Product>()
//                    .setQuery(FirebaseDatabase.getInstance().getReference().child("product").startAt(s).endAt(s),Product.class)
//                    .build();
//
//        adapter = new ProductAdapter(options);
//        Log.d("furqan", String.valueOf(options.getSnapshots()));
//        Log.d("stringfurqan",s);
//        adapter.startListening();
//        view.setAdapter(adapter);


}