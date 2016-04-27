package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.MessagesFragment;
import com.example.ela.pelinmobile.Model.MessageModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    List<MessageModel> messages;
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MessagesAdapter(List<MessageModel> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemCount() {
        return messages == null ? 0 : messages.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.bind(messages.get(position), listener);
        holder.sender.setText(messages.get(position).getTargetUser().getName());
        holder.sendAt.setText(messages.get(position).getCreatedAt());
//        holder.senderImg.setImageResource(messages.get(position).senderImg);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView messageCv;
        ImageView senderImg;
        TextView sender, messageContent, sendAt;

        public ViewHolder(final View itemView) {
            super(itemView);

            messageCv = (CardView) itemView.findViewById(R.id.messageCv);
            senderImg = (ImageView) itemView.findViewById(R.id.senderImg);
            sender = (TextView) itemView.findViewById(R.id.sender);
            sendAt = (TextView) itemView.findViewById(R.id.sendAt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnItemClick(itemView, getLayoutPosition(), false);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.OnItemClick(itemView, getLayoutPosition(), true);
                    }
                    return true;
                }
            });
        }


//        public void bind(final MessageModel messages, final OnItemClickListener listener) {
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.OnItemClick(messages, getLayoutPosition(), );
//                }
//            });
//        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, boolean isLongClick);
    }

    public void removeItem(int position) {
        messages.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
