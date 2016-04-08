package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.ListTugas;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 8/04/16.
 */
public class ListTugasAdapter extends RecyclerView.Adapter<ListTugasAdapter.ViewHolder> {

    private List<ListTugas.Tugas> tugases;


    public ListTugasAdapter(List<ListTugas.Tugas> tugases) {
        this.tugases = tugases;
    }

    @Override
    public int getItemCount() {
        return tugases.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(tugases.get(position).title);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tugas_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.jdl_tugas);
        }
    }
}
