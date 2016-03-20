package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.MessagesFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    List<MessagesFragment.MyMessage> messages;

    public MessagesAdapter(List<MessagesFragment.MyMessage> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sender.setText(messages.get(position).sender);
        holder.messageContent.setText(messages.get(position).content);
        holder.sendAt.setText(messages.get(position).sendAt);
        holder.senderImg.setImageResource(messages.get(position).senderImg);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView messageCv;
        ImageView senderImg;
        TextView sender, messageContent, sendAt;

        public ViewHolder(View itemView) {
            super(itemView);

            messageCv = (CardView) itemView.findViewById(R.id.messageCv);
            senderImg = (ImageView) itemView.findViewById(R.id.senderImg);
            sender = (TextView) itemView.findViewById(R.id.sender);
            messageContent = (TextView) itemView.findViewById(R.id.messageContent);
            sendAt = (TextView) itemView.findViewById(R.id.sendAt);
        }
    }
}
