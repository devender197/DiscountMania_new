package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.MerchantUpdatePfActivity;

import java.util.ArrayList;
import java.util.List;

public class RevAdapter_admin_mer_list extends RecyclerView.Adapter<RevAdapter_admin_mer_list.ViewHolder>  implements Filterable {

    private List<ModelMerchantList> rs;
    private List<ModelMerchantList> temp;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    public RevAdapter_admin_mer_list(Context context, List<ModelMerchantList> rs) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext=context;
        this.rs=rs;
        temp=rs;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_admin_merchantlist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.km.setText(rs.get(position).getBCat());
        holder.detail.setText(rs.get(position).getLocality());
        holder.head.setText(rs.get(position).getBName());
        holder.mobil.setText(rs.get(position).getNumber()+"");
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return rs.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView km,detail,head,mobil;
        ImageView edit;

        ViewHolder(View itemView) {
            super(itemView);
            km = (TextView) itemView.findViewById(R.id.km);
            detail = (TextView) itemView.findViewById(R.id.detail);
            head = (TextView) itemView.findViewById(R.id.head);
            mobil = (TextView) itemView.findViewById(R.id.mob);
            edit=(ImageView)itemView.findViewById(R.id.imageView3);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,MerchantUpdatePfActivity.class).putExtra("mid",rs.get(getAdapterPosition()).getId())/*.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)*/);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, rs.get(getAdapterPosition()).getId());
        }
    }

    private Filter fRecords;

    //return the filter class object
    @Override
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RevAdapter_admin_mer_list.RecordFilter();
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
                results.values = rs;
                results.count = rs.size();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
                //  ArrayList<String> fRecords = new ArrayList<String>();
                List<ModelMerchantList> fRecords = new ArrayList<>();

                for (ModelMerchantList s : temp) {
                    // if(s.getbSubCat()!=null && s.getPin()!=null && s.getStreet()!=null && s.getLocality()!=null && s.getDistrict()!=null && s.getState()!=null && s.getbDesc()!=null) {
                    try {
                        if (s.getBName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getNumber().toString().contains(charString) ||
                                s.getLocality().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getStreet().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getState().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getDistrict().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getBCat().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getBSubCat().toUpperCase().trim().contains(charString.toString().toUpperCase().trim())
                                  ) {
                            fRecords.add(s);
                        }
                    } catch (Exception e) {
                    }

                   /* }
                    else if(s.getbSubCat()==null)
                    {
                        if (s.getbDesc().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getbName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim())

                                ) {
                            fRecords.add(s);
                        }
                    }*/
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
            rs= (List<ModelMerchantList>) results.values;
            notifyDataSetChanged();
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
        void onItemClick(View view, int position);
    }
}