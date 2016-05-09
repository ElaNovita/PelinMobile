package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.MessageDetail;
import com.example.ela.pelinmobile.Model.MessageDetailModel;
import com.example.ela.pelinmobile.Model.ReplyMsgModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 30/03/16.
 */
public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.ViewHolder> {

    List<MessageDetailModel> messagesDetails;
    private final int LEFT = 1, RIGHT = 2;
    CustomDateFormatter cdf = new CustomDateFormatter();

    public MessageDetailAdapter(List<MessageDetailModel> messagesDetails) {
        this.messagesDetails = messagesDetails;
    }

    @Override
    public int getItemCount() {
        return messagesDetails.size();
    }

    public void addItem(MessageDetailModel messageDetailModel) {
        this.messagesDetails.add(messageDetailModel);
    }

    @Override
    public int getItemViewType(int position) {
        if (messagesDetails.get(position).isMe()) {
            return RIGHT;
        } else {
            return LEFT;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_left, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case LEFT:
                View v1 = inflater.inflate(R.layout.message_left, parent, false);
                viewHolder = new ViewHolder(v1);
                break;
            case RIGHT:
                View v2 = inflater.inflate(R.layout.message_right, parent, false);
                viewHolder = new ViewHolder(v2);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.content.setText(messagesDetails.get(position).getText());
        try {
            holder.time.setText(cdf.format(messagesDetails.get(position).getSent()));
        } catch (Exception e) {

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, content;


        public ViewHolder(View itemView) {
            super(itemView);

            time = (TextView) itemView.findViewById(R.id.sentAts);
            content = (TextView) itemView.findViewById(R.id.txtMsgs);
        }
    }



}
