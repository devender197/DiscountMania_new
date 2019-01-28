package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;

import java.util.ArrayList;

public class Adapter_top_sub_adm  extends BaseAdapter {

    private Context mContext;
    ArrayList<String> gridViewString=new ArrayList<>();
    ArrayList<String> numm=new ArrayList<>();

    public Adapter_top_sub_adm(Context context, ArrayList<String> gridViewString,ArrayList<String> numm) {
        mContext = context;
        this.gridViewString = gridViewString;
        this.numm=numm;
    }

    @Override
    public int getCount() {
        return gridViewString.size();
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
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.grid_top_sub_adm, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            TextView textView1 = (TextView) gridViewAndroid.findViewById(R.id.num);
            textViewAndroid.setText(gridViewString.get(i));
            textView1.setText(numm.get(i));
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
