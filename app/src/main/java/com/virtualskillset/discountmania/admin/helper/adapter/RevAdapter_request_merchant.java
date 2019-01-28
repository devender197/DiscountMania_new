package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.virtualskillset.discountmania.R;
import com.virtualskillset.discountmania.admin.AdminReqMerchantsActivity;
import com.virtualskillset.discountmania.admin.helper.model.ModelMerchantList;
import com.virtualskillset.discountmania.merchant.MerDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class RevAdapter_request_merchant extends RecyclerView.Adapter<RevAdapter_request_merchant.MyViewHolder> {

        private Context mContext;
        private List<ModelMerchantList> reqList =new ArrayList<>();

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView detail,head,mob,crtedBy;
            public Button aprove;

            public MyViewHolder(View view) {
                super(view);
                detail = (TextView) view.findViewById(R.id.detail);
                head = (TextView) view.findViewById(R.id.head);
                mob=(TextView)view.findViewById(R.id.mobb);
                aprove=(Button) view.findViewById(R.id.mySwitch);
                crtedBy=(TextView)view.findViewById(R.id.crt);
            }
        }


        public RevAdapter_request_merchant(Context mContext,List<ModelMerchantList> reqList) {
            this.mContext=mContext;
            this.reqList=reqList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_request_merchant, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder,final int i) {

            holder.detail.setText(reqList.get(i).getLocality());
            holder.head.setText(reqList.get(i).getBName());
            holder.mob.setText(reqList.get(i).getNumber() + "");
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


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, MerDetailsActivity.class).putExtra("mid", reqList.get(i).getId()));
                }
            });


        holder.aprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic goes here
                try {
                    if (mContext instanceof AdminReqMerchantsActivity) {
                        ((AdminReqMerchantsActivity) mContext).aprvReq(reqList.get(i).getId());
                    }

                } catch (Exception e) {
                    Toast.makeText(mContext,"Error : Merchant id not found",Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

    @Override
    public int getItemCount() {
        return reqList.size();
    }

    }

