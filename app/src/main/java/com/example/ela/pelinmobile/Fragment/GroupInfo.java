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

    @Bind(R.id.group_name)
    TextView nama_group;
    @Bind(R.id.group_dosen)
    TextView nama_dosen;
    @Bind(R.id.group_member)
    TextView member;
    @Bind(R.id.group_class)
    TextView classes;
    @Bind(R.id.group_semester)
    TextView semester;

    public GroupInfo() {
        // Required empty public constructor
    }

    public static GroupInfo newInstance(String title) {
        GroupInfo frag = new GroupInfo();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String title = getArguments().getString("title", "Info Group");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated =  inflater.inflate(R.layout.fragment_group_info, container, false);
        Button close = (Button) inflated.findViewById(R.id.group_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return inflated;
    }

}
