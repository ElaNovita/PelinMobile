package com.example.ela.pelinmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.example.ela.pelinmobile.Adapter.MessageDetailAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 30/03/16.
 */
public class MessageDetail extends AppCompatActivity {

    List<MessagesDetail> messagesDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        initData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dayat Eds");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.messageDetailRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        MessageDetailAdapter adapter = new MessageDetailAdapter(messagesDetails);
        recyclerView.setAdapter(adapter);
    }

    public class MessagesDetail {
        public String time, content;
        public int id;

        public MessagesDetail(String time, String content, int id) {
            this.time = time;
            this.content = content;
            this.id = id;
        }
    }

    private void initData() {
        messagesDetails = new ArrayList<>();
        messagesDetails.add(new MessagesDetail("08.00", "Hey", 1));
        messagesDetails.add(new MessagesDetail("08.01", "Import the downloaded android websockets library into Eclipse workspace", 2));
        messagesDetails.add(new MessagesDetail("08.03", "Now add this project as a Library to our project.", 1));
        messagesDetails.add(new MessagesDetail("08.05", "Finally open the main activity class ", 2));
        messagesDetails.add(new MessagesDetail("08.10", " A web socket is created using WebSocketClient class and it has all the callback methods like onConnect, onMessage and onDisconnect.", 1));
        messagesDetails.add(new MessagesDetail("08.11", "Okay", 2));
        messagesDetails.add(new MessagesDetail("08.15", "Next, override the onBindViewHolder method to configure the ViewHolder with actual data that needs to be displayed. ", 1));
        messagesDetails.add(new MessagesDetail("08.30", "The following methods are used for configuring the individual RecyclerView.ViewHolder objects", 2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
