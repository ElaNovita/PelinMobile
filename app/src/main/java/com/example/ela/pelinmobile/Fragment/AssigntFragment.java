package com.example.ela.pelinmobile.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ela.pelinmobile.Adapter.AssigntListAdapter;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.Fragment.GroupDetail.ListTugas;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssigntFragment extends Fragment {

    private List<Assignt> assignts;

    public AssigntFragment() {
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
        View inflated = inflater.inflate(R.layout.fragment_assignt, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.assigntRv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        AssigntListAdapter adapter = new AssigntListAdapter(assignts, new AssigntListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Assignt assignt) {
                Intent intent = new Intent(getActivity(), ListTugas.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        return inflated;
    }

    public class Assignt {
        public String title, due;

        public Assignt(String title, String due) {
            this.title = title;
            this.due = due;
        }
    }

    private void initData() {
        assignts = new ArrayList<>();

        assignts.add(new Assignt("Tugas 1", "2 hari lagi"));
        assignts.add(new Assignt("Tugas 2", "3 hari lagi"));
        assignts.add(new Assignt("Tugas 3", "5 hari lagi"));
        assignts.add(new Assignt("Tugas 4", "7 hari lagi"));
    }

}
