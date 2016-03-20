package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.AssigntFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 17/03/16.
 */
public class AssigntListAdapter extends RecyclerView.Adapter<AssigntListAdapter.ViewHolder> {

    List<AssigntFragment.Assignt> assignts;

    public AssigntListAdapter(List<AssigntFragment.Assignt> assignts) {
        this.assignts = assignts;
    }

    @Override
    public int getItemCount() {
        return assignts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignt_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(assignts.get(position).title);
        holder.dueTime.setText(assignts.get(position).due);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueTime;

        public ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.judulTugas);
            dueTime = (TextView) itemView.findViewById(R.id.dueTime);
        }
    }
}
