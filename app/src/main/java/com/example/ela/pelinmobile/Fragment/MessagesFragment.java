package com.example.ela.pelinmobile.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.MessagesAdapter;
import com.example.ela.pelinmobile.Fragment.GroupDetail.CreateMessage;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MessageInterface;
import com.example.ela.pelinmobile.MessageDetail;
import com.example.ela.pelinmobile.Model.MessageModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private List<MessageModel> messages;
    RecyclerView recyclerView;


    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_messages, container, false);

        MessageInterface messageInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MessageInterface.class);
        Call<List<MessageModel>> call = messageInterface.getMessages();
        call.enqueue(new Callback<List<MessageModel>>() {
            @Override
            public void onResponse(Call<List<MessageModel>> call, Response<List<MessageModel>> response) {
                List<MessageModel> messageModels = response.body();

                MessagesAdapter adapter = new MessagesAdapter(messageModels, new MessagesAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(MessageModel messages) {
                        Intent intent = new Intent(getActivity(), MessageDetail.class);
                        startActivity(intent);
                    }
                });

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<MessageModel>> call, Throwable t) {

            }
        });

        recyclerView = (RecyclerView) inflated.findViewById(R.id.messageRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addMessage);

        MessagesAdapter adapter = new MessagesAdapter(messages, new MessagesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MessageModel messages) {

            }
        });

        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return inflated;
    }


    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateMessage createMessage = CreateMessage.newInstance("Enter Username");

        createMessage.show(fragmentManager, "Enter Username");
    }

}
