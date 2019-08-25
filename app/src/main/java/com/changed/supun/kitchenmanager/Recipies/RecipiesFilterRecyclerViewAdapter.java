package com.changed.supun.kitchenmanager.Recipies;

import android.content.Context;
import android.support.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.List;

public class RecipiesFilterRecyclerViewAdapter extends RecyclerView.Adapter<RecipiesFilterRecyclerViewAdapter.ViewHolder> {
    //variables
    public List<KitchenItem> filterList;
    private Context context;


    //array list to store the string values of the check boxes
    public ArrayList<String> checkBoxItems = new ArrayList<String>();

    //constructor
    public RecipiesFilterRecyclerViewAdapter(List<KitchenItem> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }

    @NonNull
    @Override
    public RecipiesFilterRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflating the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kitchen, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecipiesFilterRecyclerViewAdapter.ViewHolder holder, int position) {

        KitchenItem filterM = filterList.get(position);
        //setting the values for the text views
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

        //constructor
        public ViewHolder(View view) {
            super(view);
            //initializing the views which is in the inflating layout
            itemName = (TextView) view.findViewById(R.id.item_id);
            itemWeight = (TextView) view.findViewById(R.id.title);
            itemAvailability = (CheckBox) view.findViewById(R.id.checkbox_availability);

            //item click event listener
            view.setOnClickListener(this);

            //checkbox event handling
            itemAvailability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        for (int i = 0; i < filterList.size(); i++) {
                            Log.d("Recipe Recycler", itemName.getText().toString());

                            if (filterList.get(i).getItemName().equalsIgnoreCase(itemName.getText().toString())) {
                                String value = filterList.get(i).getItemName().toString();
                                //adding the selected items to an arraylist
                                checkBoxItems.add(value);
                                Log.d("Recipe Recycler", value);
                            }
                        }


                    } else {

                        for (int i = 0; i < filterList.size(); i++) {
                            Log.d("Recipe Recycler", itemName.getText().toString());

                            if (filterList.get(i).getItemName().equalsIgnoreCase(itemName.getText().toString())) {
                                String value = filterList.get(i).getItemName().toString();
                                checkBoxItems.remove(value);
                            }
                        }

                    }
                }
            });

        }

        @Override
        public void onClick(View v) {

        }
    }


}
