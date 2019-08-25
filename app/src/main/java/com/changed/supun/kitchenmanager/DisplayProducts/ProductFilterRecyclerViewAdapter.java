package com.changed.supun.kitchenmanager.DisplayProducts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.changed.supun.kitchenmanager.KitchenItem;
import com.changed.supun.kitchenmanager.R;

import java.util.List;

public class ProductFilterRecyclerViewAdapter extends
        RecyclerView.Adapter<ProductFilterRecyclerViewAdapter.ViewHolder> {

    //variables
    public List<KitchenItem> filterList;
    private Context context;


    //constructor
    public ProductFilterRecyclerViewAdapter(List<KitchenItem> filterModelList
            , Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }


    @Override
    public ProductFilterRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                          int viewType) {
        //inflating the layout
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kitchen, parent, false);
        //creating a view holder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KitchenItem filterM = filterList.get(position);
        //setting the values
        holder.itemName.setText(filterM.getItemName());
        holder.itemWeight.setText("" + filterM.getWeight());
        if (filterM.getAvailability() == 1) {
            holder.itemAvailability.setChecked(true);
        } else {
            holder.itemAvailability.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    //inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //variables
        public TextView itemName;
        public TextView itemWeight;
        public CheckBox itemAvailability;


        public ViewHolder(View view) {
            super(view);
            itemName = (TextView) view.findViewById(R.id.item_id);
            itemWeight = (TextView) view.findViewById(R.id.title);
            itemAvailability = (CheckBox) view.findViewById(R.id.checkbox_availability);


            //item click event listener
            view.setOnClickListener(this);

            //checkbox click event handling
            itemAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        for (int i = 0; i < filterList.size(); i++) {
                            Log.d("set", itemName.getText().toString());

                            if (filterList.get(i).getItemName().equalsIgnoreCase(itemName.getText().toString())) {
                                filterList.get(i).setAvailability(1);
                            }
                        }

                      /*  Toast.makeText(ProductFilterRecyclerViewAdapter.this.context,
                                "selected item is " + itemName.getText() ,
                                Toast.LENGTH_LONG).show();*/
/*
                        int size = filterList.size();
                        String finalsize = String.valueOf(size);
                        Toast.makeText(ProductFilterRecyclerViewAdapter.this.context,"Length is " + finalsize,Toast.LENGTH_SHORT).show();*/

                    } else {

                        for (int i = 0; i < filterList.size(); i++) {
                            Log.d("set", itemName.getText().toString());

                            if (filterList.get(i).getItemName().equalsIgnoreCase(itemName.getText().toString())) {
                                filterList.get(i).setAvailability(0);
                            }
                        }
                    }
                }
            });


        }


        @Override
        public void onClick(View v) {
            //    TextView itemName = (TextView) v.findViewById(R.id.product_count_txt);
            //show more information about items
        }
    }
}
