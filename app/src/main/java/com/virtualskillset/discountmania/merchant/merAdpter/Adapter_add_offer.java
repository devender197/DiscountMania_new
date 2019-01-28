package com.virtualskillset.discountmania.merchant.merAdpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.merchant.MerAddOfferActivity;
import com.virtualskillset.discountmania.merchant.retro.ModelMerOffer;

import java.util.ArrayList;
import java.util.List;

public class Adapter_add_offer extends BaseAdapter {

    private Context mContext;
    private List<ModelMerOffer> lst;
 /*   private final int[] gridViewImageId;
    private final int[] cal;
    private final String[] detailstr;
    private final String[] headname;
    private final String[] dis;
    private final String[] tim;
    private final int[] mod;*/


    public Adapter_add_offer(Context context,List<ModelMerOffer> lst ) {
        mContext = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }



    @Override
    public Object getItem(int i) {




        return null;
    }

    @Override
    public long getItemId(int i) {



        return 0;
    }


    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(     Context.LAYOUT_INFLATER_SERVICE );
            v = inflater.inflate(R.layout.grid_add_offer, parent, false);
        } else {
            v = (View) convertView;
        }


        TextView km = (TextView) v.findViewById(R.id.km);
            ImageView imageViewAndroid = (ImageView) v.findViewById(R.id.shp_img);
            ImageView phn = (ImageView) v.findViewById(R.id.cal);

            TextView detail = (TextView) v.findViewById(R.id.detail);
            TextView head = (TextView) v.findViewById(R.id.head);
        TextView offer = (TextView) v.findViewById(R.id.dis);
        TextView time = (TextView) v.findViewById(R.id.tim);
     /*   ImageView edt = (ImageView) v.findViewById(R.id.mod);*/



            ///imageViewAndroid.setImageResource(R.drawable.ofr);
            phn.setImageResource(R.drawable.dlt);
        head.setText(lst.get(i).getTag());
        detail.setText(lst.get(i).getCat());
        km.setText("₹"+lst.get(i).getPrice());
        offer.setText(lst.get(i).getDisc()+"%");
        time.setText(lst.get(i).getExpire());
       // edt.setImageResource(R.drawable.mod);

        //int loader = R.drawable.ofr;
        String image_url = "http://discountmania.org/images/merchant/offer/"+lst.get(i).getImage();

        // ImageLoader class instance
       /* ImageLoader imgLoader = new ImageLoader(mContext);
        imgLoader.DisplayImage(image_url, loader, imageViewAndroid);*/
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ofr)
                .error(R.drawable.disbnrr);
        //.centerCrop()
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
        //.priority(Priority.HIGH);
        Glide.with(mContext)
                .load(image_url)
                .apply(options)
                //   .placeholder(R.drawable.noim)
                // .apply(RequestOptions.circleCropTransform())
                .into(imageViewAndroid);

            phn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Logic goes here
                    if(mContext instanceof MerAddOfferActivity) {
                        ((MerAddOfferActivity) mContext).delOffer(lst.get(i).getId());
                    }
                }
            });





        return v;
        }

}