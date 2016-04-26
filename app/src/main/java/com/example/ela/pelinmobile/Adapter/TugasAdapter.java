package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.TugasFragment;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 19/03/16.
 */
public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.ViewHolder> {

    private List<TugasModel> tugases;
    OnItemClickListener listener;

    public TugasAdapter(List<TugasModel> tugases, OnItemClickListener listener) {
        this.tugases = tugases;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return tugases.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tugas_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tugases.get(position), listener);
        holder.title.setText(tugases.get(position).getTitle());
        holder.dueTime.setText(tugases.get(position).getDue_date());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueTime;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.judulTugas);
            dueTime = (TextView) itemView.findViewById(R.id.dueTime);
        }

        public void bind(final TugasModel tugas, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(tugas);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(TugasModel tugas);
    }
}


