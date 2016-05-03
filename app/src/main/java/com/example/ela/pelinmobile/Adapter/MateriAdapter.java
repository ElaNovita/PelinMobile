package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriFragment;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ViewHolder> {

    List<MateriModel> materiModels;

    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MateriAdapter(List<MateriModel> materiModels) {
        this.materiModels = materiModels;
    }

    @Override
    public int getItemCount() {
        return materiModels.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(materiModels.get(position).getTitle());
        holder.time.setText(materiModels.get(position).getCreatedAt());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;

        public ViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.materiTitle);
            time = (TextView) itemView.findViewById(R.id.posted);

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
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, boolean isLongClick);
    }

    public void removeItem(int position) {
        materiModels.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}
