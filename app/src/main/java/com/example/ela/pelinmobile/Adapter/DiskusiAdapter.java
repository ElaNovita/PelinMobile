package com.example.ela.pelinmobile.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class DiskusiAdapter extends RecyclerView.Adapter<DiskusiAdapter.ViewHolder> {

    List<DiskusiFragment.Diskusi> diskusis;

    public DiskusiAdapter(List<DiskusiFragment.Diskusi> diskusis) {
        this.diskusis = diskusis;
    }

    @Override
    public int getItemCount() {
        return diskusis.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diskusi_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dSender.setText(diskusis.get(position).sender);
        holder.dSendAt.setText(diskusis.get(position).dSendAt);
        holder.dContent.setText(diskusis.get(position).dContent);
        holder.dLike.setText(diskusis.get(position).dLike);
        holder.dCount.setText(diskusis.get(position).dCount);
        holder.dSenderImg.setImageResource(diskusis.get(position).dImgSender);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView dCv;
        TextView dSender, dSendAt, dContent, dLike, dCount;
        ImageView dSenderImg;

        public ViewHolder(View itemView) {
            super(itemView);

            dCv = (CardView) itemView.findViewById(R.id.diskusiCv);
            dSender = (TextView) itemView.findViewById(R.id.sender);
            dSendAt = (TextView) itemView.findViewById(R.id.sendAt);
            dContent = (TextView) itemView.findViewById(R.id.diskusiContent);
            dLike = (TextView) itemView.findViewById(R.id.likeCount);
            dCount = (TextView) itemView.findViewById(R.id.replyDiskusicount);
            dSenderImg = (ImageView) itemView.findViewById(R.id.senderImg);
        }


    }
}
