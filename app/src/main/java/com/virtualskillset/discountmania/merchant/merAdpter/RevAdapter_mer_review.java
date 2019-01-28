package com.virtualskillset.discountmania.merchant.merAdpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.merchant.retro.ModelMerReview;

import java.util.ArrayList;
import java.util.List;

public class RevAdapter_mer_review extends RecyclerView.Adapter<RevAdapter_mer_review.ViewHolder> {

    private List<ModelMerReview.Response> reviews=new ArrayList<>();
    private LayoutInflater mInflater;
    //private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RevAdapter_mer_review(Context context, List<ModelMerReview.Response> reviews) {
        this.mInflater = LayoutInflater.from(context);
        this.reviews=reviews;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_mer_view_review, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.review.setText(reviews.get(position).getDesc());
        holder.date.setText(reviews.get(position).getUpdatedAt());
        holder.rating.setText(reviews.get(position).getRating()+".0");
        holder.name.setText(reviews.get(position).getName());
        holder.rb.setRating(reviews.get(position).getRating());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return reviews.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{ /*implements View.OnClickListener {*/
        TextView review,name,rating,date;
        RatingBar rb;

        ViewHolder(View itemView) {
            super(itemView);
            review = itemView.findViewById(R.id.android_gridview_text);
            name = itemView.findViewById(R.id.rv);
            rating = itemView.findViewById(R.id.rating);
            date = itemView.findViewById(R.id.time);
            rb = itemView.findViewById(R.id.ratingBar3);
            //itemView.setOnClickListener(this);
        }

       /* @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }*/
    }

   /* // convenience method for getting data at click position
    String getItem(int id) {
        return gridViewString[id];
    }*/

    // allows clicks events to be caught
  /*  public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }*/
}
