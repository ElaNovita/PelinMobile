package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ViewHolder> {

    List<MateriFragment.Materi> materis;

    public MateriAdapter(List<MateriFragment.Materi> materis) {
        this.materis = materis;
    }

    @Override
    public int getItemCount() {
        return materis.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(materis.get(position).title);
        holder.time.setText(materis.get(position).posted);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.materiTitle);
            time = (TextView) itemView.findViewById(R.id.posted);
        }
    }
}
