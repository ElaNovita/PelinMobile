package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ela.pelinmobile.Adapter.TugasAdapter;
import com.example.ela.pelinmobile.AddTugas;
import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TugasFragment extends Fragment {

    private List<Tugas> tugases;

    public TugasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_tugas, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.tugastRv);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.tugas);
        FloatingActionButton floatingActionButton = (FloatingActionButton) inflated.findViewById(R.id.addTugas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TugasAdapter adapter = new TugasAdapter(tugases, new TugasAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Tugas tugas) {
                Intent intent = new Intent(getActivity(), AssigntDetail.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTugas.class);
                startActivity(intent);
            }
        });

        return inflated;
    }

    public class Tugas {
        public String title, due;

        public Tugas(String title, String due) {
            this.title = title;
            this.due = due;
        }
    }

    public void initData() {
        tugases = new ArrayList<>();
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
        tugases.add(new Tugas("Tugas 1", "21 March 16 11.59 PM"));
    }
}
