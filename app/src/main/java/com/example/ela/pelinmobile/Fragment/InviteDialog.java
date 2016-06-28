package com.example.ela.pelinmobile.Fragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MemberInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteDialog extends DialogFragment {

    View inflated;
    EditText username;
    ImageButton invite;
    String user;

    public InviteDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_invite_dialog, container, false);

        username = (EditText) inflated.findViewById(R.id.username);
        invite = (ImageButton) inflated.findViewById(R.id.invite);

        Bundle args = getArguments();
        final int groupId = args.getInt("groupId");


        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = username.getText().toString();

                reqJson(user, groupId);
                dismiss();
            }
        });

        return inflated;
    }

    private void reqJson(String username, int groupId) {

        MemberInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(MemberInterface.class);
        Call<ApproveModel> call = service.invite(groupId, username);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Log.d("respon", "onResponse: invite " + response.code());

            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }

    public static InviteDialog newInstance(String title) {
        InviteDialog frag = new InviteDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
}
