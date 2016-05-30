package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiDetail;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.Model.ReplyModel;
import com.example.ela.pelinmobile.R;

import java.text.ParseException;
import java.util.List;

/**
 * Created by e on 29/03/16.
 */
public class DiskusiDetailAdapter extends RecyclerView.Adapter<DiskusiDetailAdapter.ViewHolder> {

    private List<ReplyModel> detailDiskusis;
    Context context;
    static ImageView img;
    CustomDateFormatter cdf = new CustomDateFormatter();

    public DiskusiDetailAdapter(List<ReplyModel> detailDiskusis, Context context) {
        this.detailDiskusis = detailDiskusis;
        this.context = context;
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

        holder.nama.setText(detailDiskusis.get(position).getUser().getName());
        holder.content.setText(detailDiskusis.get(position).getText());
        try {
            holder.time.setText(cdf.format(detailDiskusis.get(position).getCreatedAt()));
        } catch (ParseException e) {
            //
        }

        String imgUrl = detailDiskusis.get(position).getUser().getPhoto().getSmall();
        if (imgUrl == null) {
            img.setImageResource(R.drawable.purple1);
        } else {
            Glide.with(context).load(imgUrl).into(img);
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, content, time;

        public ViewHolder(View itemView) {
            super(itemView);

            nama = (TextView) itemView.findViewById(R.id.sender);
            content = (TextView) itemView.findViewById(R.id.diskusiContent);
            time = (TextView) itemView.findViewById(R.id.sendAt);
            img = (ImageView) itemView.findViewById(R.id.senderImg);
        }

    }

    public void addItem(ReplyModel replyModel) {
        this.detailDiskusis.add(replyModel);
    }

}
