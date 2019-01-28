package com.virtualskillset.discountmania.admin.helper.adapter;

import android.Manifest;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.MainBannerActivity;
import com.virtualskillset.discountmania.merchant.retro.ModelMerBanner;

import java.util.List;

public class Adapter_add_mainbanner extends BaseAdapter {
    private Context mContext;
    private List<ModelMerBanner> lst;
  //  ProfileUpdtBannerFragment frag;


    public Adapter_add_mainbanner(Context context, List<ModelMerBanner> lst ) {
        mContext = context;
        this.lst = lst;
       // this.frag = frag;
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
            v = inflater.inflate(R.layout.grid_add_banner, parent, false);
        } else {
            v = (View) convertView;
        }

        ImageView imageViewAndroid = (ImageView) v.findViewById(R.id.shp_img);
        ImageView phn = (ImageView) v.findViewById(R.id.cal);

        /*   ImageView edt = (ImageView) v.findViewById(R.id.mod);*/



        ///imageViewAndroid.setImageResource(R.drawable.ofr);
        phn.setImageResource(R.drawable.dlt);
        // edt.setImageResource(R.drawable.mod);

      //  int loader = R.drawable.disbnrr;
        String image_url = "http://discountmania.org/images/merchant/banner/"+lst.get(i).getImage();

       /* // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(mContext);
        imgLoader.DisplayImage(image_url, loader, imageViewAndroid);*/

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
                .into(imageViewAndroid);

        phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here

                //frag.delProduct(lst.get(i).getId());
                if(mContext instanceof MainBannerActivity){
                    ((MainBannerActivity)mContext).delProduct(lst.get(i).getId());
                }

            }
        });





        return v;
    }

}

