package com.company.thedecorum.Fargments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.thedecorum.ArActivity;
import com.company.thedecorum.Lamp;
import com.company.thedecorum.LampAdapter;
import com.company.thedecorum.MainActivity;
import com.company.thedecorum.Product;
import com.company.thedecorum.ProductAdapter;
import com.company.thedecorum.R;
import com.company.thedecorum.Table;
import com.company.thedecorum.TableAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class TableFragment extends Fragment implements TableAdapter.AbcOnClickListener {

    private RecyclerView Recyclerview;
    private MainActivity mainActivity;
    private TableAdapter adapter;
    private ArrayList<Table> list;
    private DatabaseReference reference;


    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;


    TextView textView;

    public TableFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_table, container, false);

        Recyclerview = view.findViewById(R.id.recycleview);
        list = new ArrayList<>();

        //toolbar = view.findViewById(R.id.toolBar);
        // setSupportActionBar(toolbar);

        reference = FirebaseDatabase.getInstance().getReference().child("Table");
        Recyclerview.setHasFixedSize(true);
        Recyclerview.addItemDecoration(new DividerItemDecoration(
                Recyclerview.getContext(),DividerItemDecoration.VERTICAL
        ));
        Recyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new TableAdapter(getActivity(),list);
        Recyclerview.setAdapter(adapter);
        adapter.setabcOnClickListener(this);
        reference.addListenerForSingleValueEvent(listener);

        return view;
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Table p = snapshot.getValue(Table.class);
                    list.add(p);
                }
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getActivity(),"No record found",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getActivity(),databaseError.toString(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void clickme(int pos) {
        Toast.makeText(getActivity(),pos + "" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void viewdetail(int pos) {
        Table p = list.get(pos);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(p.geturl()));
        startActivity(i);
    }

    @Override
    public void ar(int pos) {
        Table p = list.get(pos);

        Intent intent = new Intent(getActivity(), ArActivity.class);
        intent.putExtra("id", String.valueOf(pos));
        startActivity(intent);
    }
}