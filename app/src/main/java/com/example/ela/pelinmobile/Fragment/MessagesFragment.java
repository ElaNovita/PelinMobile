package com.example.ela.pelinmobile.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.MessagesAdapter;
import com.example.ela.pelinmobile.Fragment.GroupDetail.CreateMessage;
import com.example.ela.pelinmobile.MessageDetail;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagesFragment extends Fragment {

    private List<MyMessage> messages;


    public MessagesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_messages, container, false);
        RecyclerView recyclerView = (RecyclerView) inflated.findViewById(R.id.messageRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addMessage);
        MessagesAdapter adapter = new MessagesAdapter(messages, new MessagesAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MyMessage message) {
                Intent intent = new Intent(getActivity(), MessageDetail.class);
                startActivity(intent);
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

    public class MyMessage {
        public String sender, content, sendAt;
        public int senderImg;

        MyMessage(String sender, String content, String sendAt, int senderImg) {
            this.sender = sender;
            this.sendAt = sendAt;
            this.content = content;
            this.senderImg = senderImg;
        }

    }

    private void initData() {
        messages = new ArrayList<>();
        messages.add(new MyMessage("Haruka Nanase", "I only swim freestyle", "07.35 AM", R.drawable.haruka));
        messages.add(new MyMessage("Akashi Seijūrō", "If you oppose me, I will kill you no matter who you are", "Thur 09.10 PM", R.drawable.sei));
        messages.add(new MyMessage("Haise Sasaki", "Rather than a person who hurts others, become the person getting hurt", "Thur 05.47 PM", R.drawable.haise));
        messages.add(new MyMessage("Levi Ackerman", "Now behave or else I’m going to have to carve you into pretty little pieces", "wed 05.30 AM", R.drawable.levi));
    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateMessage createMessage = CreateMessage.newInstance("Enter Username");

        createMessage.show(fragmentManager, "Enter Username");
    }

}
