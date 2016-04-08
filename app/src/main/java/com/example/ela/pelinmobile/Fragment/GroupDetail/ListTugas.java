package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ela.pelinmobile.Adapter.ListTugasAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 8/04/16.
 */
public class ListTugas extends AppCompatActivity {

    private List<Tugas> tugases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_tugas);
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listtugasRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListTugasAdapter adapter = new ListTugasAdapter(tugases);
        recyclerView.setAdapter(adapter);
    }

    public class Tugas {
        public String title;

        public Tugas(String title) {
            this.title = title;
        }
    }

    public void initData() {
        tugases = new ArrayList<>();
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
        tugases.add(new Tugas("namaTugas"));
    }

}
