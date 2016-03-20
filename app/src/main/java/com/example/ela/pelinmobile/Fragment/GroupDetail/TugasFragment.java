package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ela.pelinmobile.Adapter.TugasAdapter;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TugasAdapter adapter = new TugasAdapter(tugases);
        recyclerView.setAdapter(adapter);

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
