package com.virtualskillset.discountmania.admin.helper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.virtualskillset.discountmania.R;

public class Adaptergrid extends BaseAdapter {

    private Context mContext;
    private String[] gridViewString;
    private int[] gridViewImageId;

    public Adaptergrid(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
    }

    @Override
    public int getCount() {
        return gridViewString.length;
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
       // View gridViewAndroid;
        TextView textViewAndroid;
        ImageView imageViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            convertView = inflater.inflate(R.layout.grid_cat, parent, false);
            /*btn = new Button(mContext);
            btn.setLayoutParams(new GridView.LayoutParams(100, 55));
            btn.setPadding(8, 8, 8, 8);*/
            textViewAndroid = (TextView) convertView.findViewById(R.id.android_gridview_text);
            imageViewAndroid= (ImageView) convertView.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);
        }
            return convertView;

    }
}