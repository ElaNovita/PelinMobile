package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MemberFragment;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 19/03/16.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private List<MemberFragment.Member> members;
    OnitemClickListener listener;

    Context context;

    public MemberAdapter(Context context, List<MemberFragment.Member> members, OnitemClickListener listener) {
        this.members = members;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(members.get(position), listener);
        holder.username.setText(members.get(position).name);
        holder.userImg.setImageResource(members.get(position).userImg);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, nim;
        ImageView userImg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            userImg = (ImageView) itemView.findViewById(R.id.userImg);
            userImg.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        public void bind(final MemberFragment.Member member, final OnitemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(member);
                }
            });
        }
    }

    public interface OnitemClickListener {
        void OnItemClick(MemberFragment.Member member);
    }
}
