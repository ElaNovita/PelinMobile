package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 18/05/16.
 */
public class MateriDetailAdapter extends RecyclerView.Adapter<MateriDetailAdapter.ViewHolder> {

    List<MateriModel> models;
    static OnItemClickListener listener;

    public MateriDetailAdapter(List<MateriModel> models, OnItemClickListener listener) {
        this.models = models;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.nmFile.setText(models.get(position).getFileModels().get(position).getName());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nmFile;

        public ViewHolder(View itemView) {
            super(itemView);

            nmFile = (TextView) itemView.findViewById(R.id.nm_file);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getLayoutPosition(), false);
                    }
                }
            });
        }
    }

    public void removeItem(int position) {
        models.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}

