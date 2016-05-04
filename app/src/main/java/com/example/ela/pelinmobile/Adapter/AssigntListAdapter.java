package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.AssigntFragment;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 17/03/16.
 */
public class AssigntListAdapter extends RecyclerView.Adapter<AssigntListAdapter.ViewHolder> {

    List<TugasModel> tugasModels;
    OnItemClickListener listener;
    CustomDateFormatter cdf = new CustomDateFormatter();

    public AssigntListAdapter(List<TugasModel> tugasModels, OnItemClickListener listener) {
        this.tugasModels = tugasModels;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return tugasModels == null ? 0 : tugasModels.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignt_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tugasModels.get(position), listener);
        holder.title.setText(tugasModels.get(position).getTitle());
        holder.dueTime.setText(cdf.format(tugasModels.get(position).getDueDate()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueTime;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.judulTugas);
            dueTime = (TextView) itemView.findViewById(R.id.dueTime);
        }

        public void bind(final TugasModel tugasModel, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(tugasModel);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(TugasModel tugasModel);
    }
}
