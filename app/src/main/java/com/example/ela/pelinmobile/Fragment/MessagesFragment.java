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
import android.widget.Button;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.MessagesAdapter;
import com.example.ela.pelinmobile.Fragment.GroupDetail.CreateMessage;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MessageInterface;
import com.example.ela.pelinmobile.MessageDetail;
import com.example.ela.pelinmobile.Model.MessageModel;
import com.example.ela.pelinmobile.Model.ReplyMsgModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private List<MessageModel> messages;
    RecyclerView recyclerView;
    Button delete;
    MessagesAdapter adapter;
    String userId;

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
        delete = (Button) inflated.findViewById(R.id.delete);

        recyclerView = (RecyclerView) inflated.findViewById(R.id.messageRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addMessage);

        reqJson();

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

    private void reqJson() {
        MessageInterface messageInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MessageInterface.class);
        Call<List<MessageModel>> call = messageInterface.getMessages();
        call.enqueue(new Callback<List<MessageModel>>() {
            @Override
            public void onResponse(Call<List<MessageModel>> call, Response<List<MessageModel>> response) {
                final List<MessageModel> messageModels = response.body();


                adapter = new MessagesAdapter(messageModels);
                adapter.setOnItemClickListener(new MessagesAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(View view, final int position, boolean isLongClick) {
                        userId = messageModels.get(position).getUserId();
                        if (isLongClick) {
                            delete.setVisibility(View.VISIBLE);
                            delete.setText("Delete Conversation with " + messageModels.get(position).getTargetUser().getName() + "?");
                            delete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    deleteChat();
                                }
                            });

                        } else {
                            Intent intent = new Intent(getActivity(), MessageDetail.class);
                            intent.putExtra("userId", messageModels.get(position).getUserId());
                            intent.putExtra("userName", messageModels.get(position).getTargetUser().getName());
                            startActivity(intent);
                        }
                    }
                });
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<MessageModel>> call, Throwable t) {

            }
        });
    }

    private void deleteChat() {
        MessageInterface messageInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MessageInterface.class);
        Call<ResponseBody> call = messageInterface.deleteMsg(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                delete.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Deleting...", Toast.LENGTH_SHORT).show();
                reqJson();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
