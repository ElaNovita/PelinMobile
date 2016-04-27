package com.example.ela.pelinmobile.Fragment.GroupDetail;



import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MessageInterface;
import com.example.ela.pelinmobile.MessageDetail;
import com.example.ela.pelinmobile.Model.MessageDetailModel;
import com.example.ela.pelinmobile.Model.ReplyMsgModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateMessage extends DialogFragment {
    int keyCode;
    EditText txtName, msgContent;
    Button send, cancel;
    String name, content;

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

        txtName = (EditText) inflated.findViewById(R.id.txtName);
        msgContent = (EditText) inflated.findViewById(R.id.msgContent);
        send = (Button) inflated.findViewById(R.id.sendMsg);
        cancel = (Button) inflated.findViewById(R.id.cancel);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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

    public void Create() {

        ReplyMsgModel replyMsgModel = new ReplyMsgModel();
        content = msgContent.getText().toString();
        name = txtName.getText().toString();
        replyMsgModel.setText(content);

        MessageInterface messageInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MessageInterface.class);
        Call<ReplyMsgModel> call = messageInterface.sendMsg(name, replyMsgModel);
        call.enqueue(new Callback<ReplyMsgModel>() {
            @Override
            public void onResponse(Call<ReplyMsgModel> call, Response<ReplyMsgModel> response) {
                Log.d("respon", "onResponse: " + name);

                Intent intent = new Intent(getActivity(), MessageDetail.class);
                intent.putExtra("userId", name);
                intent.putExtra("userName", name);
                startActivity(intent);
                dismiss();


            }

            @Override
            public void onFailure(Call<ReplyMsgModel> call, Throwable t) {

            }
        });
    }

}
