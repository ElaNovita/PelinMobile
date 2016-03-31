package com.example.ela.pelinmobile.Fragment.GroupDetail;



import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.R;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMessage extends DialogFragment {
    int keyCode;

    public CreateMessage() {
        // Required empty public constructor
    }

    public static CreateMessage newInstance(String title) {
        CreateMessage frag = new CreateMessage();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_create_message, container, false);
        ImageView search = (ImageView) inflated.findViewById(R.id.searchMsg);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().remove(CreateMessage.this).commit();
            }
        });
        return inflated;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String title = getArguments().getString("title", "Enter Username");
        getDialog().getWindow().setTitle(title);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
    }

}
