package com.timealbum.moses.test3.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.timealbum.moses.test3.ItemObject;
import com.timealbum.moses.test3.R;

import java.util.List;

public class RVLoadMoreAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ItemObject> itemList;
    private Context context;


    public RVLoadMoreAdapter(Context context, List<ItemObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders recyclerViewHolders,final int i) {
        recyclerViewHolders.personName.setText(itemList.get(i).getName());
        recyclerViewHolders.personAddress.setText(itemList.get(i).getAddress());
        recyclerViewHolders.personPhoto.setImageResource(itemList.get(i).getPhotoID());

        recyclerViewHolders.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"Item ");
                Toast.makeText(context,"Item " + i+1 +" is clicked",Toast.LENGTH_SHORT).show();
            }
        }

        );
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

}
