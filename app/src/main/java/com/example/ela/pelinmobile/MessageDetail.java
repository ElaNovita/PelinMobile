package com.example.ela.pelinmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ela.pelinmobile.Adapter.MessageDetailAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MessageInterface;
import com.example.ela.pelinmobile.Model.MessageDetailModel;
import com.example.ela.pelinmobile.Model.ReplyMsgModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 30/03/16.
 */
public class MessageDetail extends AppCompatActivity {

    String TAG = "respon", msgContent;
    ImageView send;
    RecyclerView recyclerView;
    TextView txtMsg;
    MessageDetailAdapter adapter;
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Dayat Eds");

        send = (ImageView) findViewById(R.id.sendMsg);
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        recyclerView = (RecyclerView) findViewById(R.id.messageDetailRv);

        llm = new LinearLayoutManager(getApplicationContext());
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);

        reqJson();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                sendMsg();


                txtMsg.setText("");
            }
        });

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

    private void reqJson() {
        MessageInterface messageInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MessageInterface.class);
        Call<List<MessageDetailModel>> call = messageInterface.getMessageDetail("putu");
        call.enqueue(new Callback<List<MessageDetailModel>>() {
            @Override
            public void onResponse(Call<List<MessageDetailModel>> call, Response<List<MessageDetailModel>> response) {
                List<MessageDetailModel> messageDetail = response.body();

                adapter = new MessageDetailAdapter(messageDetail);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MessageDetailModel>> call, Throwable t) {

            }
        });
    }

    private void sendMsg() {

        ReplyMsgModel replyMsgModel = new ReplyMsgModel();
        msgContent = txtMsg.getText().toString();
        replyMsgModel.setText(msgContent);

        MessageDetailModel m = new MessageDetailModel();
        m.setMe(true);
        m.setText(msgContent);
        adapter.addItem(m);
        llm.scrollToPositionWithOffset(adapter.getItemCount()-1, 0);

        MessageInterface messageInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MessageInterface.class);
        Call<ReplyMsgModel> call = messageInterface.sendMsg("putu", replyMsgModel);
        call.enqueue(new Callback<ReplyMsgModel>() {
            @Override
            public void onResponse(Call<ReplyMsgModel> call, Response<ReplyMsgModel> response) {
                ReplyMsgModel reply = response.body();
//                reqJson();
                Log.d(TAG, "onResponse: " + msgContent);

            }

            @Override
            public void onFailure(Call<ReplyMsgModel> call, Throwable t) {

                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
