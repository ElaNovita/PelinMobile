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
import com.example.ela.pelinmobile.HomeDosen;
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

    EditText grouptitle, group_smster, group_desc, jurusan;
    Button create, cancel;

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
        grouptitle = (EditText) inflated.findViewById(R.id.group_name);
        group_smster = (EditText) inflated.findViewById(R.id.group_semester);
        group_desc = (EditText) inflated.findViewById(R.id.group_desc);
        jurusan = (EditText) inflated.findViewById(R.id.jurusan);
        cancel = (Button) inflated.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = grouptitle.getText().toString();
                String semester = group_smster.getText().toString();
                String major = jurusan.getText().toString();

                if (title.matches("")) {
                    grouptitle.setError("Nama Group Tidak Boleh Kosong");
                } else if (semester.matches("")) {
                    group_smster.setError("Semester Tidak Boleh Kosong");
                } else if (major.matches("")) {
                    jurusan.setError("Jurusan Tidak Boleh Kosong");
                } else {
                    GroupInterface groupInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(GroupInterface.class);

                    GroupModel groupModel = new GroupModel();
                    groupModel.setTitle(title);
                    groupModel.setMajor(jurusan.getText().toString());
                    groupModel.setSemester(Integer.parseInt(group_smster.getText().toString()));

                    Call<GroupModel> call = groupInterface.createGroup(groupModel);
                    call.enqueue(new Callback<GroupModel>() {
                        @Override
                        public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                            GroupModel group = response.body();
                            Log.d("respon", group.getTitle());
                        }

                        @Override
                        public void onFailure(Call<GroupModel> call, Throwable t) {

                        }
                    });


                    Intent intent = new Intent(getActivity(), HomeDosen.class);
                    startActivity(intent);
                    dismiss();
                }
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
