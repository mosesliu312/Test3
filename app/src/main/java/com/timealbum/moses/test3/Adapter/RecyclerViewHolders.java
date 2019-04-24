package com.timealbum.moses.test3.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.timealbum.moses.test3.R;

class RecyclerViewHolders extends RecyclerView.ViewHolder {
    public TextView personName;
    public TextView personAddress;
    public ImageView personPhoto;

    public RecyclerViewHolders(@NonNull View itemView) {
        super(itemView);

        personName = itemView.findViewById(R.id.person_name);
        personAddress = itemView.findViewById(R.id.person_address);
        personPhoto = itemView.findViewById(R.id.circleView);
    }
}
