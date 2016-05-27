package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Fragment.GroupDetail.ListTugas;
import com.example.ela.pelinmobile.Model.Submitted;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 8/04/16.
 */
public class ListTugasAdapter extends RecyclerView.Adapter<ListTugasAdapter.ViewHolder> {

    private List<Submitted> tugases;
    static CardView cardView;
    static OnItemClickListener listener;
    Context context;


    public ListTugasAdapter(List<Submitted> tugases, Context context, OnItemClickListener listener) {
        this.listener = listener;
        this.tugases = tugases;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return tugases.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.sender.setText(tugases.get(position).getUser().getName());
        holder.desc.setText(tugases.get(position).getText());
        holder.nim.setText(tugases.get(position).getUser().getStudent().getNim());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tugas_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView sender, file, desc, nim;
        ImageView download;


        public ViewHolder(View itemView) {
            super(itemView);

            sender = (TextView) itemView.findViewById(R.id.sender);
            desc = (TextView) itemView.findViewById(R.id.desc);
            download = (ImageView) itemView.findViewById(R.id.download);
            nim = (TextView) itemView.findViewById(R.id.nim);

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

}

