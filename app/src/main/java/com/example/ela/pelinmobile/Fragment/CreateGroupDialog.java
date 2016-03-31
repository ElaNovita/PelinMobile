package com.example.ela.pelinmobile.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.R;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupDialog extends DialogFragment {


    @Bind(R.id.group_name)
    EditText group_name;
    @Bind(R.id.group_desc)
    EditText group_desc;



    public CreateGroupDialog() {
        // Required empty public constructor
    }

    public static CreateGroupDialog newInstance(String title) {
        CreateGroupDialog frag = new CreateGroupDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.create_group, container);
        Button button = (Button) inflated.findViewById(R.id.btn_create);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupDetail.class);
                startActivity(intent);
                dismiss();
            }
        });
        return inflated;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "Enter Group Name");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );

    }
}
