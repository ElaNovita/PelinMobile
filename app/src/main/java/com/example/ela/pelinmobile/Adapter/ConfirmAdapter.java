package com.example.ela.pelinmobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Fragment.GroupDetail.ConfirmMember;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 9/04/16.
 */
public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.ViewHolder> {
    private List<ConfirmMember.Confirm> confirms;
    Context context;

    public ConfirmAdapter(List<ConfirmMember.Confirm> confirms, Context context) {
        this.confirms = confirms;
        this.context = context;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(confirms.get(position).name);
        holder.nim.setText(confirms.get(position).nim);
        holder.id.setText(confirms.get(position).id);
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, nim, id;
        ImageButton confirm, reject;

        public ViewHolder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.confimr_nm);
            nim = (TextView) itemView.findViewById(R.id.confirm_nim);
            id = (TextView) itemView.findViewById(R.id.itemPosition);
            confirm = (ImageButton) itemView.findViewById(R.id.confirm);
            reject = (ImageButton) itemView.findViewById(R.id.reject);

        }


    }

    public void removeItem(int position) {
        confirms.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public void addToGroup(int position) {

    }


}
