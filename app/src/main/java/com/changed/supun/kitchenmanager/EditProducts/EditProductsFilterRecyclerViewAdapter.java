package com.changed.supun.kitchenmanager.EditProducts;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changed.supun.kitchenmanager.KitchenItem;
import com.changed.supun.kitchenmanager.R;

import java.util.List;

public class EditProductsFilterRecyclerViewAdapter extends RecyclerView.Adapter<EditProductsFilterRecyclerViewAdapter.ViewHolder> {

    //public arraylist to store kitchen items
    public List<KitchenItem> filterList;

    //varibale for the context
    private Context context;

    //CONSTRUCTOR
    public EditProductsFilterRecyclerViewAdapter(List<KitchenItem> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public EditProductsFilterRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflating the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.editproduct_item, parent, false);
        //creating a view holder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditProductsFilterRecyclerViewAdapter.ViewHolder holder, int position) {

        KitchenItem filterM = filterList.get(position);
        //setting the values
        holder.itemName.setText(filterM.getItemName());
        holder.itemId.setText("" + filterM.getId());

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    //inner class ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        //variables
        public TextView itemId;
        public TextView itemName;


        //CONSTRUCTOR
        public ViewHolder(View view) {
            super(view);
            itemId = (TextView) view.findViewById(R.id.item_id);
            itemName = (TextView) view.findViewById(R.id.product_name);


            //item click event lister
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //initializing the text view
            TextView itemName = (TextView) v.findViewById(R.id.product_name);


            //getting details of the kitchen item
            for (KitchenItem item : filterList) {
                if (item.getItemName().equalsIgnoreCase(itemName.getText().toString())) {

                    int id = item.getId();

                    String kitchenItemName = item.getItemName();
                    int availability = item.getAvailability();

                    double weight = item.getWeight();
                    //conversion

                    double price = item.getPrice();
                    String description = item.getDescription();

                    //opening  a new intent and parsing values through the intent
                    Context context = v.getContext();
                    Intent intent = new Intent(context, Edit_Infomation.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", kitchenItemName);
                    intent.putExtra("weight", weight);
                    intent.putExtra("availability", availability);
                    intent.putExtra("price", price);
                    intent.putExtra("description", description);
                    context.startActivity(intent);


                }
            }


        }
    }


}
