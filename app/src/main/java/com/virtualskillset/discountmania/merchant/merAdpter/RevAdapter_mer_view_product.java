package com.virtualskillset.discountmania.merchant.merAdpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.merchant.retro.ModelMerProduct;

import java.util.List;

public class RevAdapter_mer_view_product extends RecyclerView.Adapter<RevAdapter_mer_view_product.ViewHolder> {

    private List<ModelMerProduct> lst;
    private LayoutInflater mInflater;
    private Context mContext;
    private RevAdapter_mer_view_product.ItemClickListener mClickListener;

    // data is passed into the constructor
    public RevAdapter_mer_view_product(Context context, List<ModelMerProduct> lst) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext=context;
        this.lst=lst;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public RevAdapter_mer_view_product.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_pro_dtl, parent, false);
        return new RevAdapter_mer_view_product.ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull RevAdapter_mer_view_product.ViewHolder holder, int position) {
        holder.textViewAndroid.setText(lst.get(position).getDesc());
        //int loader = R.drawable.disbnrr;
        String image_url = "http://discountmania.org/images/merchant/product/"+lst.get(position).getImage();

        // ImageLoader class instance
        /*ImageLoader imgLoader = new ImageLoader(mContext);
        imgLoader.DisplayImage(image_url, loader, holder.imageViewAndroid);*/
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.disbnrr)
                .error(R.drawable.disbnrr);
        //.centerCrop()
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
        //.priority(Priority.HIGH);
        Glide.with(mContext)
                .load(image_url)
                .apply(options)
                //   .placeholder(R.drawable.noim)
                // .apply(RequestOptions.circleCropTransform())
                .into(holder.imageViewAndroid);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return lst.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewAndroid;
        ImageView imageViewAndroid;

        ViewHolder(View itemView) {
            super(itemView);
            textViewAndroid = itemView.findViewById(R.id.android_gridview_text);
            imageViewAndroid= itemView.findViewById(R.id.android_gridview_image);
            imageViewAndroid.setOnClickListener(this);
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
    public void setClickListener(RevAdapter_mer_view_product.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

