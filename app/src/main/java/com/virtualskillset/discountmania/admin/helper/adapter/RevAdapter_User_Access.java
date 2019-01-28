package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.user.model.ModelUserData;

import java.util.ArrayList;
import java.util.List;

public class RevAdapter_User_Access extends RecyclerView.Adapter<RevAdapter_User_Access.MyViewHolder>implements Filterable {

    private Context mContext;
    private List<ModelUserData> reqList,temp;
    private LayoutInflater mInflater;
    private AdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView head,mob,crtedBy,paid;
       // Switch paid,block;
       // ImageView set;

        MyViewHolder(View itemView) {
            super(itemView);
            head = itemView.findViewById(R.id.head);
            mob=itemView.findViewById(R.id.mobb);
            paid=itemView.findViewById(R.id.paid);
           /* paid=itemView.findViewById(R.id.mySwitchPaid);
            block=itemView.findViewById(R.id.mySwitchBlock);*/
            crtedBy=itemView.findViewById(R.id.crt);
           // set=itemView.findViewById(R.id.set);
        }
    }


    public RevAdapter_User_Access(Context mContext, List<ModelUserData> reqList,AdapterListener listener) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext=mContext;
        this.reqList=reqList;
        this.listener=listener;
        temp=reqList;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        final ModelUserData req=reqList.get(i);

        holder.head.setText(req.getName());
        holder.mob.setText(req.getNumber()+"");
        holder.crtedBy.setText("Created At "+req.getCreatedAt());
        if(req.getPaid()==1)
        {
            holder.paid.setText("Paid");
        }
        else {
            holder.paid.setText("");
        }
       /* holder.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

     /*   if(req.getPaid()==1)
        {
            holder.paid.setChecked(true);
        }
        if(req.getStatus()==0)
        {
            holder.block.setChecked(true);
        }*/

       /* holder. block.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mContext instanceof AdminUserAccess) {
                    int val=1;
                    if(isChecked) { val=0; }
                    try {
                        ((AdminUserAccess) mContext).blockMer(val,req.getId());
                    }catch (Exception e){}

                }
            }
        });

        holder.paid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mContext instanceof AdminUserAccess) {
                    int val=0;
                    if(isChecked) { val=1; }
                    try {
                        ((AdminUserAccess) mContext).paidMer(val,req.getId());
                    }catch (Exception e){}
                }
            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onContactSelected(reqList.get(holder.getAdapterPosition()));
            }
        });


    }
    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_user_access, parent, false);
        return new MyViewHolder(view);
    }


    // total number of cells

    @Override
    public int getItemCount() {
        return reqList.size();
    }
    private Filter fRecords;

    //return the filter class object
    @Override
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RevAdapter_User_Access.RecordFilter();
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
                List<ModelUserData> fRecords = new ArrayList<>();

                for (ModelUserData s : temp) {
                    // if(s.getbSubCat()!=null && s.getPin()!=null && s.getStreet()!=null && s.getLocality()!=null && s.getDistrict()!=null && s.getState()!=null && s.getbDesc()!=null) {
                    try {
                        if (
                                s.getName().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getNumber().contains(charString) ||
                                s.getPincode().toString().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getAddress().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getEmail().toUpperCase().trim().contains(charString.toString().toUpperCase().trim()) ||
                                s.getProfession().toUpperCase().trim().contains(charString.toString().toUpperCase().trim())
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
            reqList= (List<ModelUserData>) results.values;
            notifyDataSetChanged();
        }
    }

    public interface AdapterListener {
        void onContactSelected(ModelUserData contact);
    }
}
