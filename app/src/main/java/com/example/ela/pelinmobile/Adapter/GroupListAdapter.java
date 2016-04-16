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
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.List;


/**
 * Created by ela on 17/03/16.
 */
public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    List<Group> groups;
    private final OnItemClickListener listener;


    public GroupListAdapter(List<Group> groups, OnItemClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(groups.get(position), listener);
        holder.groupTitle.setText(groups.get(position).title);
        holder.dosenName.setText(groups.get(position).name);
        holder.countMember.setText(Integer.toString(groups.get(position).count));
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

        ViewHolder(View view) {
            super(view);

            groupTitle = (TextView) view.findViewById(R.id.group_name);
            dosenName = (TextView) view.findViewById(R.id.nama_dosen);
            countMember = (TextView) view.findViewById(R.id.member_count);
            groupPict = (ImageView) view.findViewById(R.id.group_pict);
        }

        public void bind(final Group group, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(group);
                }
            });
        }
    }



}
