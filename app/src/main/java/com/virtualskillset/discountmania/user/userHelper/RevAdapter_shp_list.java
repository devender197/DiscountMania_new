package com.virtualskillset.discountmania.user.userHelper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.merchant.FullImageActivity;
import com.virtualskillset.discountmania.user.AUserMerList;
import com.virtualskillset.discountmania.user.model.ModelUsrMerLst;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class RevAdapter_shp_list extends RecyclerView.Adapter<RevAdapter_shp_list.MyViewHolder>  implements Filterable {

    private Context mContext;
    private int Call_PERMISSION_CODE = 23;
    private List<ModelUsrMerLst> reqList=new ArrayList<>();
    private List<ModelUsrMerLst> temp=new ArrayList<>();
    private AdapterListener listener;
   /* private double dlat,dlon;*/


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView detail,km,head,dis;
        public ImageView phn,profileImg;

        public MyViewHolder(View view) {
            super(view);
            km = (TextView) view.findViewById(R.id.km);
            phn = (ImageView) view.findViewById(R.id.cal);
            detail = (TextView) view.findViewById(R.id.detail);
            dis = (TextView) view.findViewById(R.id.dis);
            head = (TextView) view.findViewById(R.id.head);
            profileImg=(ImageView)view.findViewById(R.id.shp_img);
        }
    }


    public RevAdapter_shp_list(Context mContext, List<ModelUsrMerLst> reqList,AdapterListener listener) {
        this.mContext=mContext;
        this.reqList=reqList;
        this.listener=listener;
        temp=reqList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_cus_shp_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int i) {

        if(reqList.get(i).getbSubCat()==null || reqList.get(i).getbSubCat().equals(""))
        {
            holder.km.setText(reqList.get(i).getbCat());
        }
        else
        {holder.km.setText(reqList.get(i).getbCat()+" - "+reqList.get(i).getbSubCat());}
        holder.phn.setImageResource(R.drawable.cal);
        holder.detail.setText(reqList.get(i).getLocality());
        holder.head.setText(reqList.get(i).getbName());
      //  double dkm=distance(26.263863,73.008957,reqList.get(i).getLati(),reqList.get(i).getLongi());
        if(reqList.get(i).getDistt()!=0.0 && reqList.get(i).getDistt()<500.0) {
            String str=new DecimalFormat("##.#").format(reqList.get(i).getDistt());
            holder.dis.setText(str + " km");
        }

        //int loader = R.drawable.disbnrr;
        String image_url = "http://discountmania.org/images/merchant/profile/" +reqList.get(i).getImg();

        // ImageLoader class instance
        /*ImageLoader imgLoader = new ImageLoader(mContext);
        imgLoader.DisplayImage(image_url, loader, holder.profileImg);*/
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
                .into(holder.profileImg);

        holder.phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here
                if(isReadCallNotAllowed()){
                    //If permission is already having then showing the toast
                    // Toast.makeText(MainActivity.this,"You already have the permission",Toast.LENGTH_LONG).show();
                    //Existing the method with return
                    // return;
                    requestCallPermission();
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:" + reqList.get(i).getNumber()));
                    mContext.startActivity(intent);}

            }
        });

        holder.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pr=new Intent(mContext,FullImageActivity.class);
                pr.putExtra("imagepath","http://discountmania.org/images/merchant/profile/"+reqList.get(i).getImg());
                pr.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pr);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mContext.startActivity(new Intent(mContext, MerDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra("mid", reqList.get(i).getId()));
                listener.onContactSelected(reqList.get(i));
            }
        });


    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }

    //We are calling this method to check the permission status
    private boolean isReadCallNotAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED)
            return false;

        //If permission is not granted returning false
        return true;
    }

    //Requesting permission
    private void requestCallPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale((AUserMerList)mContext,Manifest.permission.CALL_PHONE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions((AUserMerList)mContext,new String[]{Manifest.permission.CALL_PHONE},Call_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == Call_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(mContext,"Now you can call Merchants",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(mContext,"Permission denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    private Filter fRecords;

    //return the filter class object
    @Override
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RecordFilter();
        }
        return fRecords;
    }

    //filter class
    private class RecordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charString) {

            FilterResults results = new FilterResults();

            //Implement filter logic
            // if edittext is null return the actual list
            if (charString == null || charString.length() == 0) {
                //No need for filter
                results.values = temp;
                results.count = temp.size();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
              //  ArrayList<String> fRecords = new ArrayList<String>();
                List<ModelUsrMerLst> fRecords = new ArrayList<>();

                for (ModelUsrMerLst s : temp) {
                    // if(s.getbSubCat()!=null && s.getPin()!=null && s.getStreet()!=null && s.getLocality()!=null && s.getDistrict()!=null && s.getState()!=null && s.getbDesc()!=null) {
                    try {
                        if (s.getbDesc().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getbName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getbSubCat().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getLocality().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getStreet().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getDistrict().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getState().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getPin().toUpperCase().trim().contains(charString.toString().toUpperCase().trim())
                                ) {
                            fRecords.add(s);
                        }
                    } catch (Exception e) {
                    }
                }
                results.values = fRecords;
                results.count = fRecords.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charString,FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
           // reqList = (ArrayList<String>) results.values;
            reqList= (List<ModelUsrMerLst>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface AdapterListener {
        void onContactSelected(ModelUsrMerLst contact);
    }
}