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
import com.example.ela.pelinmobile.Adapter.NotifListAdapter;
import com.example.ela.pelinmobile.Fragment.GroupDetail.ConfirmMember;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifFragment extends Fragment {

    private List<Notif> notifs;


    public NotifFragment() {
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
        View inflated = inflater.inflate(R.layout.fragment_notif, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.notifRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NotifListAdapter adapter = new NotifListAdapter(notifs, new NotifListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Notif notif) {
                Intent intent = new Intent(getActivity(), ConfirmMember.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return inflated;
    }



    public class Notif {
        public String title, content;

        public Notif(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    private void initData() {
        notifs = new ArrayList<>();

        notifs.add(new Notif("Notif 1", "ini adalah isi dari notifikasi yang pertama"));
        notifs.add(new Notif("Notif 2", "ini adalah isi dari notifikasi yang kedua"));
        notifs.add(new Notif("Notif 3", "ini adalah isi dari notifikasi yang ketiga"));
    }



}
