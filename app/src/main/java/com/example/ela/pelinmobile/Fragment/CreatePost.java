package com.example.ela.pelinmobile.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiDetail;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePost extends DialogFragment {


    public CreatePost() {
        // Required empty public constructor
    }

    public static CreatePost newInstance(String title) {
        CreatePost frag = new CreatePost();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated =  inflater.inflate(R.layout.fragment_create_post, container, false);
        ImageView send = (ImageView) inflated.findViewById(R.id.sendMsg);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiskusiDetail.class);
                startActivity(intent);
                dismiss();
            }
        });

        return inflated;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "Enter Post");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
    }
}
