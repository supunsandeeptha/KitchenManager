package com.changed.supun.kitchenmanager.Search;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.changed.supun.kitchenmanager.R;


public class SearchAdapter extends CursorAdapter {
    //variables for text views;
    TextView textName,textId,textDescription;
    private LayoutInflater inflater;

    //constructor
    public SearchAdapter(Context context, Cursor cursor, int flags){
        super(context,cursor,flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //inflating with the item layout
        View v = inflater.inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder();
        //initializing the textviews inside the item layout
        viewHolder.txtId = (TextView) v.findViewById(R.id.txtId);
        viewHolder.txtName = (TextView) v.findViewById(R.id.txtName);
        viewHolder.txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        v.setTag(viewHolder);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        //setting the item name and description
        viewHolder.txtName.setText(cursor.getString(cursor.getColumnIndex("NAME")));
        viewHolder.txtId.setText(cursor.getString(cursor.getColumnIndex("_id")));
        viewHolder.txtDescription.setText(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));

    }

    //inner class
    static class ViewHolder {
        TextView txtId;
        TextView txtName;
        TextView txtDescription;
    }
}
