package com.example.ela.pelinmobile.Adapter;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ela.pelinmobile.Fragment.GroupListFragment;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.List;


/**
 * Created by ela on 17/03/16.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    List<GroupModel> groups;
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public GroupListAdapter(List<GroupModel> groups, OnItemClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return groups == null ? 0 : groups.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.groupTitle.setText(groups.get(position).getTitle());
        holder.dosenName.setText(groups.get(position).getTeacher().getName());
        holder.countMember.setText(Integer.toString(groups.get(position).getMember()));
        String chars = new StringBuilder().append("").append(holder.groupTitle.getText().charAt(0)).toString();
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int randomColor = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(chars, randomColor);
        holder.groupPict.setImageDrawable(drawable);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView groupTitle, dosenName, countMember;
        ImageView groupPict;

        ViewHolder(final View view) {
            super(view);

            groupTitle = (TextView) view.findViewById(R.id.group_name);
            dosenName = (TextView) view.findViewById(R.id.nama_dosen);
            countMember = (TextView) view.findViewById(R.id.member_count);
            groupPict = (ImageView) view.findViewById(R.id.group_pict);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(view, getLayoutPosition(), false);
                    }
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(view, getLayoutPosition(), true);
                    }
                    return true;
                }
            });
        }

    }

    public void removeItem(int position) {
        groups.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
