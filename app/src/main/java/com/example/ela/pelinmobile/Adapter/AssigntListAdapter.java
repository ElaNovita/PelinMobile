package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.AssigntFragment;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.example.ela.pelinmobile.R;

import java.text.ParseException;
import java.util.List;

/**
 * Created by ela on 17/03/16.
 */
public class AssigntListAdapter extends RecyclerView.Adapter<AssigntListAdapter.ViewHolder> {

    List<TugasModel> tugasModels;
    OnItemClickListener listener;
    CustomDateFormatter cdf = new CustomDateFormatter();
    Context context;

    public AssigntListAdapter(List<TugasModel> tugasModels, Context context, OnItemClickListener listener) {
        this.tugasModels = tugasModels;
        this.listener = listener;
        this.context = context;
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
        if (tugasModels.get(position).isPassed()) {
            holder.passed.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            holder.passed.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }

        try {
            holder.dueTime.setText(cdf.getTimeLater(tugasModels.get(position).getDueDate()));
        } catch (ParseException e) {

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueTime;
        LinearLayout passed;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.judulTugas);
            dueTime = (TextView) itemView.findViewById(R.id.dueTime);
            passed = (LinearLayout) itemView.findViewById(R.id.passed);
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
