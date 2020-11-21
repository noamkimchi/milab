package com.example.app_v3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

public class HimymPage extends AppCompatActivity {

    private RecyclerView recyclerView;

    private int[] hImages =
                    {R.drawable.ted,R.drawable.marshall,
                    R.drawable.lily,R.drawable.barney,R.drawable.robin};

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_himym_page);

        recyclerView = findViewById(R.id.h_recycler);
        layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);


        List<String> hList = Arrays.asList(getResources().getStringArray(R.array.hNames));
        HimymAdapter adapter = new HimymAdapter(hImages,hList);
        recyclerView.setAdapter(adapter);
    }
}