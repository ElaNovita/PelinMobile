package com.example.ela.pelinmobile.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiDetail;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.DiskusiInterface;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.Model.NewPostModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreatePost extends DialogFragment {

    ImageView create;
    EditText post;
    String postTxt, TAG = "respon";

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


        create = (ImageView) inflated.findViewById(R.id.sendMsg);
        post = (EditText) inflated.findViewById(R.id.txtName);

        Bundle args = getArguments();
        final int groupId = args.getInt("groupId");
        Log.d("groupId", Integer.toString(groupId));

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postTxt = post.getText().toString();

                DiskusiInterface diskusiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(DiskusiInterface.class);

                NewPostModel diskusiModel = new NewPostModel();
                diskusiModel.setText(postTxt);

                Call<DiskusiModel> call = diskusiInterface.createPost(groupId, diskusiModel);
                call.enqueue(new Callback<DiskusiModel>() {
                    @Override
                    public void onResponse(Call<DiskusiModel> call, Response<DiskusiModel> response) {
                        try {
                            DiskusiModel model = response.body();

                            Log.d(TAG, Integer.toString(response.code()));
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: ", e);
                        }
                    }

                    @Override
                    public void onFailure(Call<DiskusiModel> call, Throwable t) {

                    }
                });

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
