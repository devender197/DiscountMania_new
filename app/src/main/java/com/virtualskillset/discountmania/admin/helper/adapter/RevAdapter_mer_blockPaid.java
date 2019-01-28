package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.MerDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class RevAdapter_mer_blockPaid extends RecyclerView.Adapter<RevAdapter_mer_blockPaid.MyViewHolder> implements Filterable {

    private Context mContext;
    private List<ModelMerchantList> reqList ,temp;
    private AdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView detail,head,mob,crtedBy,prime;
        public Switch paid,block,aprove;
        public ImageView set;

        public MyViewHolder(View view) {
            super(view);
            detail = (TextView) view.findViewById(R.id.detail);
            head = (TextView) view.findViewById(R.id.head);
            mob=(TextView)view.findViewById(R.id.mobb);
            paid=(Switch)view.findViewById(R.id.mySwitchPaid);
            block=(Switch)view.findViewById(R.id.mySwitchBlock);
            aprove=(Switch)view.findViewById(R.id.mySwitchAprove);
            crtedBy=(TextView)view.findViewById(R.id.crt);
            set=(ImageView)view.findViewById(R.id.btn);
            prime=(TextView)view.findViewById(R.id.paid);
        }
    }


    public RevAdapter_mer_blockPaid(Context mContext,List<ModelMerchantList> reqList,AdapterListener listener) {
        this.mContext=mContext;
        this.reqList=reqList;
        this.listener=listener;
        temp=reqList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_mer_blockpaid, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int i) {

        holder.detail.setText(reqList.get(i).getLocality());
        holder.head.setText(reqList.get(i).getBName());
        holder.mob.setText(reqList.get(i).getNumber()+"");
        if(reqList.get(i).getPaid()==1)
        {
            holder.prime.setText("Paid");
        }
        else {
            holder.prime.setText("");
        }
        /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(reqList.get(i).getCreatedAt()));*/
       // String dt=getDateCurrentTimeZone(1539158532);
        try {
            if (reqList.get(i).getCreatedBy().equals("9876543210")) {
                holder.crtedBy.setText("Created By Main Admin");
            } else if (reqList.get(i).getCreatedBy().equals(reqList.get(i).getNumber()+"")) {
                holder.crtedBy.setText("Created By Merchant Self");
            } else {
                holder.crtedBy.setText("Created By " + reqList.get(i).getCreatedBy());
            }
        }catch (Exception e){holder.crtedBy.setText("Unknown");}
       /* if(reqList.get(i).getApproved()==1)
        {
            holder.aprove.setChecked(true);
        }
        if(reqList.get(i).getPaid()==1)
        {
            holder.paid.setChecked(true);
        }
        if(reqList.get(i).getStatus()==0)
        {
            holder.block.setChecked(true);
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,MerDetailsActivity.class).putExtra("mid",reqList.get(i).getId()));
            }
        });

        holder.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContactSelected(reqList.get(holder.getAdapterPosition()));
            }
        });


      /*  holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here

            }
        });*/

       /* holder.aprove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mContext instanceof AdminMerBlockPaidActivity) {
                    int val=0;
                    if(isChecked) { val=1; }
                    try {
                        ((AdminMerBlockPaidActivity) mContext).aprvReq(val, reqList.get(i).getId());
                    }catch (Exception e){}
                }
            }
        });
        holder. block.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mContext instanceof AdminMerBlockPaidActivity) {
                    int val = 1;
                    if (isChecked) {
                        val = 0;
                    }
                    try {
                        ((AdminMerBlockPaidActivity) mContext).blockMer(val, reqList.get(i).getId());
                    } catch (Exception e) {
                    }
                }
            }
        });

        holder.paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mContext instanceof AdminMerBlockPaidActivity) {
                    int val = 0;
                    if (isChecked) {
                        val = 1;
                    }
                    try {
                        ((AdminMerBlockPaidActivity) mContext).paidMer(val, reqList.get(i).getId());
                    } catch (Exception e) {
                    }
                }
            }
        });
*/

    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }

    public  String getDateCurrentTimeZone(int timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
    private Filter fRecords;

    //return the filter class object
    @Override
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RevAdapter_mer_blockPaid.RecordFilter();
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
            reqList= (List<ModelMerchantList>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface AdapterListener {
        void onContactSelected(ModelMerchantList contact);
    }


}
