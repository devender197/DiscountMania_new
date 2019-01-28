package com.virtualskillset.discountmania.user.userHelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;

public class RevAdapter_category_home extends RecyclerView.Adapter<RevAdapter_category_home.ViewHolder> {

    private String[] gridViewString;
    private int[] gridViewImageId;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RevAdapter_category_home(Context context, String[] gridViewString,int[] gridViewImageId) {
        this.mInflater = LayoutInflater.from(context);
        this.gridViewString=gridViewString;
        this.gridViewImageId=gridViewImageId;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_cat, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewAndroid.setText(gridViewString[position]);
        holder.imageViewAndroid.setImageResource(gridViewImageId[position]);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return gridViewString.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewAndroid;
        ImageView imageViewAndroid;

        ViewHolder(View itemView) {
            super(itemView);
            textViewAndroid = itemView.findViewById(R.id.android_gridview_text);
            imageViewAndroid= itemView.findViewById(R.id.android_gridview_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

   /* // convenience method for getting data at click position
    String getItem(int id) {
        return gridViewString[id];
    }*/

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
