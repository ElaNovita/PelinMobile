package com.example.ela.pelinmobile.Fragment;



import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.HomeDosen;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupListFragment extends Fragment {
    RecyclerView groupRv;
    private FragmentActivity myContenxt;

    private List<Group> groups;

    public GroupListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groups = Group.initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_group_list, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.groupRv);
        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addGroup);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        GroupListAdapter adapter = new GroupListAdapter(groups, new OnItemClickListener() {
            @Override
            public void onItemClick(Group group) {
                Intent intent = new Intent(getActivity(), GroupDetail.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
//        addGroupFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), GroupDetail.class);
//                startActivity(intent);
//            }
//        });

        return inflated;


    }



    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateGroupDialog createGroupDialog = CreateGroupDialog.newInstance("Create New Group");
        createGroupDialog.show(fragmentManager, "title");
    }


}
