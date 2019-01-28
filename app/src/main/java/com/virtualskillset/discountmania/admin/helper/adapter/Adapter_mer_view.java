package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;

import java.util.ArrayList;

public class Adapter_mer_view extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> gridViewString =new ArrayList<>();
    private ArrayList<Integer> gridViewImageId =new ArrayList<>();

    public Adapter_mer_view(Context context, ArrayList<String>gridViewString , ArrayList<Integer> gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
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
            gridViewAndroid = inflater.inflate(R.layout.grid_pro_dtl, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString.get(i));
            imageViewAndroid.setImageResource(gridViewImageId.get(i));
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}