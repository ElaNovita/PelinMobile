package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ela.pelinmobile.Adapter.MateriAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateriFragment extends Fragment {

    private List<Materi> materis;


    public MateriFragment() {
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
        View inflated =  inflater.inflate(R.layout.fragment_materi, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.materiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MateriAdapter adapter = new MateriAdapter(materis);
        recyclerView.setAdapter(adapter);

        return inflated;
    }

    public class Materi {
        public String title, posted;

        public Materi(String title, String posted) {
            this.title = title;
            this.posted = posted;
        }
    }

    private void initData() {
        materis = new ArrayList<>();
        materis.add(new Materi("Judul materi pertama", "12 March 02.12 PM"));
        materis.add(new Materi("Judul materi kedua", "10 March 08.45 AM"));
        materis.add(new Materi("Judul materi ketiga", "07 March 03.00 PM"));
        materis.add(new Materi("Judul materi keempat", "03 March 02.03 PM"));
        materis.add(new Materi("Judul materi kelima", "24 Feb 01.27 PM"));
        materis.add(new Materi("Judul materi pertama", "12 March 02.12 PM"));
        materis.add(new Materi("Judul materi kedua", "10 March 08.45 AM"));
        materis.add(new Materi("Judul materi ketiga", "07 March 03.00 PM"));
        materis.add(new Materi("Judul materi keempat", "03 March 02.03 PM"));
        materis.add(new Materi("Judul materi kelima", "24 Feb 01.27 PM"));


    }

}
