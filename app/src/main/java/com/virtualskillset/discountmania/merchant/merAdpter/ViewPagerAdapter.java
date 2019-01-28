package com.virtualskillset.discountmania.merchant.merAdpter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.merchant.FullImageActivity;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> images=new ArrayList<>();
   // private ArrayList<Bitmap> images = new ArrayList<Bitmap>();
   // private Integer[] images = {R.drawable.a,R.drawable.b,R.drawable.c};
    ImageView imageView;

    public ViewPagerAdapter(Context context,ArrayList<String> images) {
        this.context = context;
        this.images=images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
      //  imageView.setImageBitmap(images.get(position));
        //imageView.setImageResource(images[position]);
        //int loader = R.drawable.disbnrr;
        String image_url = "http://discountmania.org/images/merchant/banner/"+images.get(position);

        // ImageLoader class instance
        /*ImageLoader imgLoader = new ImageLoader(context);
        imgLoader.DisplayImage(image_url, loader, imageView);*/
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.disbnrr)
                .error(R.drawable.disbnrr);
        //.centerCrop()
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
        //.priority(Priority.HIGH);
        Glide.with(context)
                .load(image_url)
                .apply(options)
                //   .placeholder(R.drawable.noim)
                // .apply(RequestOptions.circleCropTransform())
                .into(imageView);

      view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent pr = new Intent(context, FullImageActivity.class);
                    pr.putExtra("imagepath", "http://discountmania.org/images/merchant/banner/" + images.get(position));
                    context.startActivity(pr);
                }catch (Exception e){}

            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}
