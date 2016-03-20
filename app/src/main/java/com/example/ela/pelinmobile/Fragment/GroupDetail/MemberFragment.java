package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Adapter.MemberAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {

    List<Member> members;


    public MemberFragment() {
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
        View inflated = inflater.inflate(R.layout.fragment_member, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.memberRv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        MemberAdapter adapter = new MemberAdapter(getContext(), members);
        recyclerView.setAdapter(adapter);
        return inflated;
    }

    public class Member {
        public String name, nim;
        public int userImg;

        public Member(String name,  int userImg) {
            this.name = name;
            this.userImg = userImg;
        }
    }

    private void initData() {



        members = new ArrayList<>();
        members.add(new Member("Akashi Seijuro",  R.drawable.seb));
        members.add(new Member("Akashi Seijuro", R.drawable.levi));
        members.add(new Member("Akashi Seijuro", R.drawable.eren));
        members.add(new Member("Akashi Seijuro",  R.drawable.ciel));
        members.add(new Member("Akashi Seijuro",  R.drawable.haise));

    }

}
