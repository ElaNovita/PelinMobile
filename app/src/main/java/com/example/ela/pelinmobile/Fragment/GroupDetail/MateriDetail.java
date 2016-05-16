package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Model.FileModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 11/05/16.
 */
public class MateriDetail extends AppCompatActivity {

    ImageView bg;
    FloatingActionButton addNewMateri;
    TextView title, date, desc;
    ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materi_detail);

        List<FileModel> fileModels;

        bg = (ImageView) findViewById(R.id.bg);
        title = (TextView) findViewById(R.id.materi_title);
        date = (TextView) findViewById(R.id.created);
        desc = (TextView) findViewById(R.id.desc);
        listView = (ListView) findViewById(R.id.listView);
        addNewMateri = (FloatingActionButton) findViewById(R.id.addMateri);

        title.setText(getIntent().getStringExtra("title"));
        date.setText(getIntent().getStringExtra("date"));
        desc.setText(getIntent().getStringExtra("desc"));

//        ArrayAdapter<List> adapter = new ArrayAdapter<List>(this, R.layout.materi_detail_item, fileModels);

        addNewMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setRandomBg() {
        int[] arrayImage = new int[]{
                
        }
    }
}
