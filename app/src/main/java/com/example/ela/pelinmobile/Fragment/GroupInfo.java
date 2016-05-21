package com.example.ela.pelinmobile.Fragment;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.R;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupInfo extends DialogFragment {

    TextView nama_group, nama_dosen, _desc, _member, _semester;

    public GroupInfo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static GroupInfo newInstance(String title, String group, String dosen, String desc, int member, int semester) {
        GroupInfo frag = new GroupInfo();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("group", group);
        args.putString("dosen", dosen);
        args.putString("desc", desc);
        args.putInt("member", member);
        args.putInt("semester", semester);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String title = getArguments().getString("title", "Ela");
        String group = getArguments().getString("group");
        String dosen = getArguments().getString("dosen");
        int member = getArguments().getInt("member");
        int semester = getArguments().getInt("semester");
        String desc = getArguments().getString("desc");


        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        nama_group.setText(group);
        nama_dosen.setText(dosen);
        _member.setText(Integer.toString(member));
        _semester.setText(Integer.toString(semester));
        _desc.setText(desc);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated =  inflater.inflate(R.layout.fragment_group_info, container, false);
        Button close = (Button) inflated.findViewById(R.id.group_close);
        nama_group = (TextView) inflated.findViewById(R.id.group_name);
        nama_dosen = (TextView) inflated.findViewById(R.id.group_dosen);
        _desc = (TextView) inflated.findViewById(R.id.group_desc);
        _member = (TextView) inflated.findViewById(R.id.group_member);
        _semester = (TextView) inflated.findViewById(R.id.group_semester);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return inflated;
    }

}
