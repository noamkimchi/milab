package com.example.app_v3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    private int[] fImages;
    private List<String> fList;

    public FriendsAdapter(int[] fImages, List<String> fList) {
        this.fImages = fImages;
        this.fList = fList;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.falbum_layout,parent,false);
        FriendsViewHolder friendsViewHolder = new FriendsViewHolder(view);
        return friendsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        String friendName = fList.get(position);
        int image_id = fImages[position];
        holder.friendImage.setImageResource(image_id);
        holder.friendName.setText(friendName);

    }

    @Override
    public int getItemCount() {
        return fImages.length;
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{

        public TextView friendName;
        public ImageView friendImage;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            friendImage = itemView.findViewById(R.id.falbum);
            friendName = itemView.findViewById(R.id.falbum_title);
        }
    }
}
