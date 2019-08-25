package com.changed.supun.kitchenmanager.Recipies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.changed.supun.kitchenmanager.R;

import java.util.List;

public class RecipeViewerRecyclerViewAdapter extends RecyclerView.Adapter<RecipeViewerRecyclerViewAdapter.ViewHolder> {

    //variables
    public List<JsonObjectModel> filterList;

    private Context context;

    //constructor
    public RecipeViewerRecyclerViewAdapter(List<JsonObjectModel> filterModelList, Context ctx) {
        filterList = filterModelList;
        context = ctx;
    }


    @NonNull
    @Override
    public RecipeViewerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_view, parent, false);
        //creating a new view holder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewerRecyclerViewAdapter.ViewHolder holder, int position) {

        JsonObjectModel filterM = filterList.get(position);
        //setting the values for the text views
        holder.itemTitle.setText(filterM.getItemTitle());
        holder.itemURL.setText(filterM.getItemURL());

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }


    //inner class
    public class ViewHolder extends RecyclerView.ViewHolder implements View

            .OnClickListener {

        //variables
        public TextView itemTitle;
        public TextView itemURL;

        public ViewHolder(View view) {
            super(view);
            //initializing the views which is in the inflating layout
            itemTitle = (TextView) view.findViewById(R.id.title);
            itemURL = (TextView) view.findViewById(R.id.url);

            //item click event listener
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            //initializing the text view
            itemTitle = (TextView) v.findViewById(R.id.title);
            String URL = "";

            //retrieving the details of the kitchen item
            for (JsonObjectModel item : filterList) {
                //finding the kitchen item from the arraylist
                if (item.getItemTitle().equalsIgnoreCase(itemTitle.getText().toString())) {
                    URL = item.getItemURL();

                    Toast.makeText(RecipeViewerRecyclerViewAdapter.this.context, URL, Toast.LENGTH_SHORT).show();
                }


            }

            //open browser when the link clicked method
            Context context = v.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(URL));
            context.startActivity(intent);
        }
    }


}
