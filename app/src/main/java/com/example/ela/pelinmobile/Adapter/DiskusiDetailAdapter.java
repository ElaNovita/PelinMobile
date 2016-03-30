package com.example.ela.pelinmobile.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiDetail;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 29/03/16.
 */
public class DiskusiDetailAdapter extends RecyclerView.Adapter<DiskusiDetailAdapter.ViewHolder> {

    private List<DiskusiDetail.DetailDiskusi> detailDiskusis;

    public DiskusiDetailAdapter(List<DiskusiDetail.DetailDiskusi> detailDiskusis) {
        this.detailDiskusis = detailDiskusis;
    }

    @Override
    public int getItemCount() {
        return detailDiskusis.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diskusi_detail_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.nama.setText(detailDiskusis.get(position).name);
        holder.content.setText(detailDiskusis.get(position).content);
        holder.time.setText(detailDiskusis.get(position).time);
        holder.img.setImageResource(detailDiskusis.get(position).img);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, content, time;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.sender);
            content = (TextView) itemView.findViewById(R.id.diskusiContent);
            time = (TextView) itemView.findViewById(R.id.sendAt);
            img = (ImageView) itemView.findViewById(R.id.senderImg);
        }

    }


}
