package com.virtualskillset.discountmania.merchant.merAdpter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.virtualskillset.discountmania.merchant.retro.ModelMerTrans;
import java.util.ArrayList;
import java.util.List;
import com.virtualskillset.discountmania.R;

public class RevAdapter_transcations extends RecyclerView.Adapter<RevAdapter_transcations.MyViewHolder>  implements Filterable {

    private Context mContext;
    private List<ModelMerTrans.Response> reqList=new ArrayList<>();
    private List<ModelMerTrans.Response> temp=new ArrayList<>();
    private AdapterListener listener;
    String role;
   /* private double dlat,dlon;*/


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView t1,t2,t3,t4,t5,mrp;
        public ImageView profileImg;

        public MyViewHolder(View view) {
            super(view);
            t1=(TextView)itemView.findViewById(R.id.order);
            t2=(TextView)itemView.findViewById(R.id.name);
            t3=(TextView)itemView.findViewById(R.id.totalamount);
            t4=(TextView)itemView.findViewById(R.id.disamnt);
            t5=(TextView)itemView.findViewById(R.id.date);
            mrp=(TextView) itemView.findViewById(R.id.subtotl);
            profileImg=(ImageView)view.findViewById(R.id.img);
        }
    }


    public RevAdapter_transcations(Context mContext, List<ModelMerTrans.Response> reqList,String role, AdapterListener listener) {
        this.mContext=mContext;
        this.reqList=reqList;
        this.role=role;
        this.listener=listener;
        temp=reqList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_mer_transc, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int i) {

        holder.t1.setText(reqList.get(i).getId()+"");
        holder.t2.setText(reqList.get(i).getName());
        holder.t3.setText("₹"+reqList.get(i).getAmount());
        holder.t4.setText("-"+reqList.get(i).getDiscount()+"%");
        holder.t5.setText(reqList.get(i).getCreatedAt());
        if(reqList.get(i).getMrp()!=null){
            holder.mrp.setText("₹"+reqList.get(i).getMrp());
        }
        holder.mrp.setPaintFlags(holder.mrp.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        //int loader = R.drawable.disbnrr;
        String image_url;
        if(role.equals("1")) {
            image_url = "http://discountmania.org/images/client/" + reqList.get(i).getImage();
        }
        else {
            image_url = "http://discountmania.org/images/merchant/profile/" + reqList.get(i).getImage();
        }

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

       /* holder.profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pr=new Intent(mContext,FullImageActivity.class);
                pr.putExtra("imagepath","http://discountmania.org/images/merchant/profile/"+reqList.get(i).getImg());
                pr.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(pr);
            }
        });*/

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
                List<ModelMerTrans.Response> fRecords = new ArrayList<>();

                for (ModelMerTrans.Response s : temp) {
                    // if(s.getbSubCat()!=null && s.getPin()!=null && s.getStreet()!=null && s.getLocality()!=null && s.getDistrict()!=null && s.getState()!=null && s.getbDesc()!=null) {
                    try {
                        if (    s.getName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getCreatedAt().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getAmount().contains(charString) ||
                                s.getId().toString().equals(charString)
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
            reqList= (List<ModelMerTrans.Response>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface AdapterListener {
        void onContactSelected(ModelMerTrans.Response contact);
    }
}