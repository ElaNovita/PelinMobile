package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Fragment.NotifFragment;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Model.NotifModel;
import com.example.ela.pelinmobile.R;

import java.text.ParseException;
import java.util.List;

/**
 * Created by ela on 17/03/16.
 */
public class NotifListAdapter extends RecyclerView.Adapter<NotifListAdapter.ViewHolder> {

    List<NotifModel> notifs;
    OnItemClickListener listener;
    Context context;
    public LinearLayout lln;
    CustomDateFormatter cdf = new CustomDateFormatter();

    public NotifListAdapter(List<NotifModel> notifs, Context context, OnItemClickListener listener) {
        this.notifs = notifs;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return notifs.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(notifs.get(position), listener);

        String name = notifs.get(position).getActor().getName();
        String verbs = notifs.get(position).getVerb();
        String target = notifs.get(position).getTarget().getTitle();
        String time = notifs.get(position).getTimestamp();
        String sender = notifs.get(position).getActor().getPhoto().getSmall();

        lln = holder.read;

        holder.title.setText(name + " " + verbs + " di grup " + target);

        if (notifs.get(position).isUnread()) {
            holder.read.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.read.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        }

        if (sender != null) {
            Glide.with(context).load(sender).into(holder.sender);
        }

        try {
            holder.timeStamp.setText(cdf.format(time));
        } catch (ParseException e) {
            //
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, timeStamp;
        LinearLayout read;
        ImageView sender;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.notifTitle);
            read = (LinearLayout) itemView.findViewById(R.id.read);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStamp);
            sender = (ImageView) itemView.findViewById(R.id.sender);
        }



        public void bind(final NotifModel notif, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(notif, getLayoutPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(NotifModel notif, int position);
    }

    public void removeItem() {
        notifs.clear();
        notifyDataSetChanged();
    }

    public void markRead(LinearLayout read) {
        read.setBackgroundResource(android.R.color.darker_gray);
    }

}
