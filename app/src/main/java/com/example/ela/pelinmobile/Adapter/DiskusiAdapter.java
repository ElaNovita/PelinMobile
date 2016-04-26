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

import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiDetail;
import com.example.ela.pelinmobile.Fragment.GroupDetail.DiskusiFragment;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.Model.ReplyModel;
import com.example.ela.pelinmobile.R;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by ela on 18/03/16.
 */
public class DiskusiAdapter extends RecyclerView.Adapter<DiskusiAdapter.ViewHolder> {

    List<DiskusiModel> diskusis;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick(DiskusiModel diskusi, int position);
    }

    public DiskusiAdapter(List<DiskusiModel> diskusis, OnItemClickListener listener) {
        this.diskusis = diskusis;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return diskusis == null ? 0 : diskusis.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diskusi_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(diskusis.get(position), listener);
        holder.dSender.setText(diskusis.get(position).getUser().getTeacher().getName());
        holder.dSendAt.setText(diskusis.get(position).getCreated_at());
        holder.dContent.setText(diskusis.get(position).getText());
        holder.dLike.setText(Integer.toString(diskusis.get(position).getVotesCount()));
        holder.dCount.setText(Integer.toString(diskusis.get(position).getCommentsCount()));
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


        public void bind(final DiskusiModel diskusi, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(diskusi, getLayoutPosition());
                }
            });
        }

    }
}
