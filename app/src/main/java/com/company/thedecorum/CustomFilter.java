package com.company.thedecorum;

import android.util.Log;
import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    ProductAdapter adapter;
    ArrayList<Product> filterList;

    public CustomFilter(ProductAdapter adapter, ArrayList<Product> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length()>0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<Product> filteredModel = new ArrayList<>();
            for(int i=0; i<filterList.size();i++){
                if(filterList.get(i).getName().toUpperCase().contains(constraint)){
                    filteredModel.add(filterList.get(i));
                }
            }
            results.count = filteredModel.size();
            results.values = filteredModel;
        }
        else
        {
            results.count = filterList.size();
            results.values = filterList;

        }
        Log.e("result", ""+results.count);
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.products = (ArrayList<Product>) results.values;
        adapter.notifyDataSetChanged();
    }


}