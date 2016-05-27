package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MemberFragment;
import com.example.ela.pelinmobile.Model.MemberModel;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by ela on 19/03/16.
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    //TODO fix the request join design

    private List<MemberModel> members;
    private Fragment fragment;
    Button kick;

    LinearLayout wrap;
    private static OnItemClickListener listener;

    Context context;
    // Define the listener interface


    // Define the method that allows the parent activity or fragment to define the listener

    public interface OnItemClickListener {
        void onItemClick(View view, int position, boolean isLongClick);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MemberAdapter(Context context, List<MemberModel> members) {
        this.members = members;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return members == null ? 0 : members.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.username.setText(members.get(position).getName());
        Log.d("photo", "onBindViewHolder: " + members.get(position).getPhoto().getSmall());
        Glide.with(context).load(members.get(position).getPhoto().getMedium()).into(holder.userImg);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, nim;
        ImageView userImg;
        Button kick;

        LinearLayout wrap;

        public ViewHolder(final View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.username);
            userImg = (ImageView) itemView.findViewById(R.id.userImg);
            userImg.setScaleType(ImageView.ScaleType.FIT_XY);
            kick = (Button) itemView.findViewById(R.id.kick);
            wrap = (LinearLayout) itemView.findViewById(R.id.wrap);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition(), false);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition(), true);
                    }
                    return true;
                }
            });
        }

    }

    public void removeItem(int position) {
        members.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
