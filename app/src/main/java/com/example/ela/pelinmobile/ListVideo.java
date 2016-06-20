package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ela.pelinmobile.Adapter.ListVideoAdapter;
import com.example.ela.pelinmobile.Model.ListVidModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 17/06/16.
 */
public class ListVideo extends AppCompatActivity {
    RecyclerView recyclerView;
    ListVideoAdapter adapter;
    List<ListVidModel> vidModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_video);

        recyclerView = (RecyclerView) findViewById(R.id.list_vidRv);



        adapter = new ListVideoAdapter(getApplicationContext(), vidModels);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(getApplicationContext(), VideoPlayer.class);
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        initData();
    }

    private void initData() {

        List<ListVidModel> datas;
        datas = new ArrayList<>();
        datas.add(new ListVidModel("Hymne For The Weekend", "YykjpeuMNEk", "ColdPlay"));
        datas.add(new ListVidModel("Aoi Shiori", "T3bxbVGWy5k", "Galileo Galilei "));
        datas.add(new ListVidModel("One Call Away", "BxuY9FET9Y4", "Charlie Puth"));
        datas.add(new ListVidModel("Hello", "YQHsXMglC9A", "Adele"));
        datas.add(new ListVidModel("Without You", "zLAhRiUeJ8E", "Oh Wonder"));


    }
}
