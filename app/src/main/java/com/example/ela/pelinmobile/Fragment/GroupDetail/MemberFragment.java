package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Adapter.MemberAdapter;
import com.example.ela.pelinmobile.Profile;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberFragment extends Fragment {

    List<Member> members;
    Button kick;
    LinearLayout wrap;


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
        kick = (Button) inflated.findViewById(R.id.kick);
        wrap = (LinearLayout) inflated.findViewById(R.id.wrap);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.memberRv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.member);
        final MemberAdapter adapter = new MemberAdapter(getContext(), members);
        adapter.setOnItemClickListener(new MemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position, boolean isLongClick) {
                if (isLongClick) {
                    kick.setText("Delete " + members.get(position).name.toLowerCase() + "?");
                    kick.setVisibility(View.VISIBLE);
                    kick.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           adapter.removeItem(position);
                        }
                    });
                } else {
                    Intent intent = new Intent(getActivity(), Profile.class);
                    startActivity(intent);
                }
            }
        });

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
        members.add(new Member("Eren",  R.drawable.eren));
        members.add(new Member("Levi", R.drawable.levi));
        members.add(new Member("Akashi Seijuro", R.drawable.eren));
        members.add(new Member("Ciel",  R.drawable.ciel));
        members.add(new Member("Haise",  R.drawable.haise));

    }

}
