package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.NotifFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 17/03/16.
 */
public class NotifListAdapter extends RecyclerView.Adapter<NotifListAdapter.ViewHolder> {

    List<NotifFragment.Notif> notifs;

    public NotifListAdapter(List<NotifFragment.Notif> notifs) {
        this.notifs = notifs;
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
        holder.title.setText(notifs.get(position).title);
        holder.content.setText(notifs.get(position).content);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, content;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.notifTitle);
            content = (TextView) itemView.findViewById(R.id.notifContent);
        }
    }
}
