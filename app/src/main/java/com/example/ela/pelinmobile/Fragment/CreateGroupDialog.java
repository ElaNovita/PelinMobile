package com.example.ela.pelinmobile.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.R;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupDialog extends DialogFragment {

    TextView grouptitle, classes, group_smster, group_desc;
    Button create;

    String title;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflated = inflater.inflate(R.layout.create_group, container);
        create = (Button) inflated.findViewById(R.id.btn_create);
        grouptitle = (TextView) inflated.findViewById(R.id.group_name);
        classes = (TextView) inflated.findViewById(R.id.group_classes);
        group_smster = (TextView) inflated.findViewById(R.id.group_semester);
        group_desc = (TextView) inflated.findViewById(R.id.group_desc);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = grouptitle.getText().toString();

                GroupInterface groupInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(GroupInterface.class);

                GroupModel groupModel = new GroupModel();
                groupModel.setTitle(title);

                Call<GroupModel> call = groupInterface.createGroup(groupModel);
                call.enqueue(new Callback<GroupModel>() {
                    @Override
                    public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                        GroupModel group = response.body();
                        Log.d("respon", title);
                    }

                    @Override
                    public void onFailure(Call<GroupModel> call, Throwable t) {

                    }
                });


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
