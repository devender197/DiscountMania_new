package com.virtualskillset.discountmania.merchant.merAdpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.merchant.retro.ModelSocialLinks;

import java.util.ArrayList;
import java.util.List;

public class RevAdapter_social_links extends RecyclerView.Adapter<RevAdapter_social_links.ViewHolder> {

    private List<ModelSocialLinks> links=new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RevAdapter_social_links(Context context, List<ModelSocialLinks> links) {
        this.mInflater = LayoutInflater.from(context);
        this.links=links;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_social_links, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(links.get(position).getType().equals("wa"))
        {
            holder.btn.setBackgroundResource(R.drawable.whts);
        }else if(links.get(position).getType().equals("fb"))
        {
            holder.btn.setBackgroundResource(R.drawable.face);
        }
        else if(links.get(position).getType().equals("tt"))
        {
            holder.btn.setBackgroundResource(R.drawable.twi);
        }
        else if(links.get(position).getType().equals("yt"))
        {
            holder.btn.setBackgroundResource(R.drawable.you);
        }
        else if(links.get(position).getType().equals("ig"))
        {
            holder.btn.setBackgroundResource(R.drawable.inst);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return links.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn;

        ViewHolder(View itemView) {
            super(itemView);
            btn= itemView.findViewById(R.id.btnn);
            btn.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition(),links.get(getAdapterPosition()).getType());
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
        void onItemClick(View view, int position ,String type);
    }
}
