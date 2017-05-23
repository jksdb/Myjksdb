package com.vx.vchat.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.vx.vchat.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by asus on 2017/5/23.
 */

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> list;
    private HashSet<String> paths = new HashSet<>();

    public SelectImageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public SelectImageAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_select_image, parent, false));
    }

    @Override
    public void onBindViewHolder(SelectImageAdapter.MyViewHolder holder, final int position) {

        Glide.with(context)
                .load(list.get(position)).override(300, 200).into(holder.image);


        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    paths.add(list.get(position));
                } else {
                    paths.remove(list.get(position));
                }
            }
        });

    }

    public HashSet<String> getPaths() {
        return paths;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private CheckBox cb;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.item_select_image_img);
            cb = (CheckBox) itemView.findViewById(R.id.item_select_image_checkBox);
        }
    }
}
