package com.virtualskillset.discountmania.merchant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;

public class FullImageActivity extends AppCompatActivity {
   // Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        try {
            if (getSupportActionBar() != null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }}catch (Exception e){}

      //  imgDisplay = (ImageView) findViewById(R.id.full_image_view);
       // btnClose = (Button) findViewById(R.id.btnClose);
        // get intent data
        Intent i = getIntent();

        // Selected image id
        String position = i.getExtras().getString("imagepath");

        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
     /*   int loader = R.drawable.disbnrr;
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(this);
        imgLoader.DisplayImage(position, loader, imageView);*/
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.disbnrr)
                .error(R.drawable.disbnrr);
        //.centerCrop()
        //.diskCacheStrategy(DiskCacheStrategy.ALL)
        //.priority(Priority.HIGH);
        Glide.with(FullImageActivity.this)
                .load(position)
                .apply(options)
                //   .placeholder(R.drawable.noim)
                // .apply(RequestOptions.circleCropTransform())
                .into(imageView);

       /* BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(_imagePaths.get(position), options);
        imgDisplay.setImageBitmap(bitmap);
*/
        // close button click event
       /* btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullImageActivity.this.finish();
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
