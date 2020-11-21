package com.example.app_v3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class HimymAdapter extends RecyclerView.Adapter<HimymAdapter.HimymViewHolder> {
    private List<String> hList;
    private int[] hImages;


    public HimymAdapter(int[] hImages, List<String> hList){
        this.hImages = hImages;
        this.hList = hList;
    }

    @NonNull
    @Override
    public HimymViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.halbum_layout,parent,false);
        HimymViewHolder himymViewHolder = new HimymViewHolder(view);
        return himymViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HimymViewHolder holder, int position) {
        String hName = hList.get(position);
        int image_id = hImages[position];
        holder.hImage.setImageResource(image_id);
        holder.hName.setText(hName);
    }

    @Override
    public int getItemCount() {
        return hImages.length;
    }

    public static class HimymViewHolder extends RecyclerView.ViewHolder{

        public TextView hName;
        public ImageView hImage;

        public HimymViewHolder(View itemView){
            super(itemView);
            hName = itemView.findViewById(R.id.halbum_title);
            hImage = itemView.findViewById(R.id.halbum);
        }
    }
}
