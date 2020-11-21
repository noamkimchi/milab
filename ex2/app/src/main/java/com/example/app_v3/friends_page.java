package com.example.app_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class friends_page extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FriendsAdapter adapter;

    private int[] fImages = {R.drawable.rachel,R.drawable.monica,R.drawable.phoebe,
                        R.drawable.joey,R.drawable.chandler,R.drawable.ross};

    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_page);

        recyclerView = findViewById(R.id.friends_recycler);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        List<String> friendsList = Arrays.asList(getResources().getStringArray(R.array.fNames));
        adapter = new FriendsAdapter(fImages, friendsList);
        recyclerView.setAdapter(adapter);
    }
}