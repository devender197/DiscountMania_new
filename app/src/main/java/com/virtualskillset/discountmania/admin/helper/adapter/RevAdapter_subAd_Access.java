package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Switch;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.AdminSubAdAccess;
import com.virtualskillset.discountmania.subadmin.helperSubAdmin.ModelSubAdList;

import java.util.List;

public class RevAdapter_subAd_Access extends RecyclerView.Adapter<RevAdapter_subAd_Access.MyViewHolder> /*implements Filterable*/ {

    private Context mContext;
    private List<ModelSubAdList> reqList,temp;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView head,mob,crtedBy;
        public Switch block;

        public MyViewHolder(View view) {
            super(view);
            head = (TextView) view.findViewById(R.id.head);
            mob=(TextView)view.findViewById(R.id.mobb);
            block=(Switch)view.findViewById(R.id.mySwitchBlock);
            crtedBy=(TextView)view.findViewById(R.id.crt);
        }
    }


    public RevAdapter_subAd_Access(Context mContext, List<ModelSubAdList> reqList) {
        this.mContext=mContext;
        this.reqList=reqList;
        temp=reqList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_subad_access, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int i) {

        holder.head.setText(reqList.get(i).getName());
        holder.mob.setText(reqList.get(i).getNumber()+"");
        /*SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(reqList.get(i).getCreatedAt()));*/
       // String dt=getDateCurrentTimeZone(1539158532);
            holder.crtedBy.setText("Created At "+reqList.get(i).getCreatedAt());

        if(reqList.get(i).getStatus()==0)
        {
            holder.block.setChecked(true);
        }

      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,MerDetailsActivity.class).putExtra("mid",reqList.get(i).getId()));
            }
        });*/


      /*  holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here

            }
        });*/


        holder. block.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mContext instanceof AdminSubAdAccess) {
                    int val=1;
                    if(isChecked) { val=0; }
                    try {
                        ((AdminSubAdAccess) mContext).blockMer(val,reqList.get(i).getId());
                    }catch (Exception e){}
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }
    private Filter fRecords;

   /* //return the filter class object
    @Override
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RevAdapter_subAd_Access.RecordFilter();
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
                List<ModelSubAdList> fRecords = new ArrayList<>();

                for (ModelSubAdList s : temp) {
                    // if(s.getbSubCat()!=null && s.getPin()!=null && s.getStreet()!=null && s.getLocality()!=null && s.getDistrict()!=null && s.getState()!=null && s.getbDesc()!=null) {
                    try {
                        if (s.getName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getNumber().contains(charString) ||
                                s.getRole().toUpperCase().trim().contains(charString.toString().toUpperCase().trim())
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
            reqList= (List<ModelSubAdList>) results.values;
            notifyDataSetChanged();
        }
    }*/



}
