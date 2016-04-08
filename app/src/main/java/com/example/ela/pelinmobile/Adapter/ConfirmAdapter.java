package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.ConfirmMember;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 9/04/16.
 */
public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.ViewHolder> {
    private List<ConfirmMember.Confirm> confirms;

    public ConfirmAdapter(List<ConfirmMember.Confirm> confirms) {
        this.confirms = confirms;
    }

    @Override
    public int getItemCount() {
        return confirms.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(confirms.get(position).name);
        holder.nim.setText(confirms.get(position).nim);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, nim;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.confimr_nm);
            nim = (TextView) itemView.findViewById(R.id.confirm_nim);
        }
    }
}
