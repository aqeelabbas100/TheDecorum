package com.company.thedecorum;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.thedecorum.Fargments.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.ar.sceneform.ux.ArFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyHolder> implements Filterable {
    public ArrayList<Table> tables;
    private Context context;
    private List<Table> list,filterList;
    private AbcOnClickListener listener;
    CustomFilter filter;


    public TableAdapter(Context context, ArrayList<Table> list) {
        this.context = context;
        this.list = list;
        this.filterList = list;
    }

    public TableAdapter(FirebaseRecyclerOptions<Table> options) {
        super();
    }

    @NonNull
    @Override
    public TableAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_layout,viewGroup,false);


        return new MyHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.MyHolder abc, int i) {
        Table p = filterList.get(i);

        abc.tvshowname.setText(p.name);
        abc.tvshowsize.setText(p.size);
        abc.tvshowprice.setText(p.price);



        Picasso.get().load(p.imageurl).into(abc.imageView);

//        abc.imageView.setImageURI(p.imageurl);
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    public void startListening() {
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            List<Table> filteredList;
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                filteredList = new ArrayList<>();
                if (charString.isEmpty()) {
                    filteredList = list;
                } else {

                    for (Table row : list) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    //   searchListFiltered = filteredList;
                }


                return new FilterResults();
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filterList = new ArrayList<>();
                filterList = filteredList;

                notifyDataSetChanged();
            }
        };
    }

//
//    @Override
//    public Filter getFilter() {
//        if(filter == null){
//            Log.e("1","1");
//            filter = new CustomFilter(this, filterList);
//        }
//        Log.e("2","2");
//
//        return filter;
//    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener
            , MenuItem.OnMenuItemClickListener {

        private TextView tvshowname,tvshowsize, tvshowprice;
        private ImageView imageView;
        private ArFragment arFragment;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvshowname = itemView.findViewById(R.id.tvshowname);
            tvshowsize = itemView.findViewById(R.id.tvshowsize);
            tvshowprice = itemView.findViewById(R.id.tvshowprice);
            imageView = itemView.findViewById(R.id.imageshowrecord);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            int p = getAdapterPosition();

            if(p != RecyclerView.NO_POSITION){
                listener.clickme(p);
            }
//            Intent intent = new Intent(itemView.getContext(),ArActivity.class);
//            intent.putExtra("name", (Parcelable) list.get(getAdapterPosition()));
//            context.startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Option");
            MenuItem cart = menu.add(Menu.NONE,1,1,"View Details");
            MenuItem ar = menu.add(Menu.NONE,2,2,"Check In AR");

            cart.setOnMenuItemClickListener(this);
            ar.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            int pos = getAdapterPosition();

            if(pos != RecyclerView.NO_POSITION){
                switch (item.getItemId()){
                    case 1:
                        listener.viewdetail(pos);
                        return true;
                    case 2:
                        listener.ar(pos);
                        return true;
                }
            }
            return false;
        }
    }

    public interface AbcOnClickListener{
        void clickme(int pos);
        void viewdetail(int pos);
        void ar(int pos);
    }

    public void setabcOnClickListener(AbcOnClickListener mlistener){
        listener =  mlistener;
    }
}