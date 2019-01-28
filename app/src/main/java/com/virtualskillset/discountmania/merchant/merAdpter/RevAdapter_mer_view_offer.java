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
import com.virtualskillset.discountmania.merchant.retro.ModelMerOffer;
import java.util.List;

public class RevAdapter_mer_view_offer extends RecyclerView.Adapter<RevAdapter_mer_view_offer.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private RevAdapter_mer_view_offer.ItemClickListener mClickListener;
    private List<ModelMerOffer> lst;


    // data is passed into the constructor
    public RevAdapter_mer_view_offer(Context context, List<ModelMerOffer> lst) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext=context;
        this.lst=lst;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public RevAdapter_mer_view_offer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.grid_mer_view_offer, parent, false);
        return new RevAdapter_mer_view_offer.ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull RevAdapter_mer_view_offer.ViewHolder holder, int position) {
       // int loader = R.drawable.disbnrr;
        String image_url = "http://discountmania.org/images/merchant/offer/"+lst.get(position).getImage();

        // ImageLoader class instance
    /*    ImageLoader imgLoader = new ImageLoader(mContext);
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

        holder.textViewAndroid.setText(lst.get(position).getTag());
        holder.catab.setText(lst.get(position).getCat());
        holder.priceab.setText(lst.get(position).getPrice());
        holder.disab.setText(lst.get(position).getDisc()+"% Off");
        holder.validab.setText(lst.get(position).getExpire());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return lst.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewAndroid,catab,priceab,disab,validab;
        ImageView imageViewAndroid;

        ViewHolder(View itemView) {
            super(itemView);
            textViewAndroid = itemView.findViewById(R.id.android_gridview_text);
            imageViewAndroid= itemView.findViewById(R.id.android_gridview_image);
            textViewAndroid = itemView.findViewById(R.id.android_gridview_text);
            catab = itemView.findViewById(R.id.cat);
            priceab = itemView.findViewById(R.id.price);
            disab = itemView.findViewById(R.id.dis);
            validab = itemView.findViewById(R.id.time);
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
    public void setClickListener(RevAdapter_mer_view_offer.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}