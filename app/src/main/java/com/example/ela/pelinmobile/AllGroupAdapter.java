package com.example.ela.pelinmobile;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ela.pelinmobile.Model.Group;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 7/04/16.
 */
public class AllGroupAdapter extends RecyclerView.Adapter<AllGroupAdapter.ViewHolder> {

    List<Group> groups;
    OnItemClickListener listener;

    public AllGroupAdapter(List<Group> groups, OnItemClickListener listener) {
        this.groups = new ArrayList<>(groups);
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_group_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(groups.get(position), listener);
        holder.groupTitle.setText(groups.get(position).title);
        holder.dosenName.setText(groups.get(position).name);
        holder.countMember.setText(Integer.toString(groups.get(position).count));
        holder.semester.setText(groups.get(position).semester);
    }

    public interface OnItemClickListener {
        void OnItemClick(Group group);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupTitle, dosenName, countMember, semester;

        ViewHolder(View view) {
            super(view);

            groupTitle = (TextView) view.findViewById(R.id.nama_group);
            dosenName = (TextView) view.findViewById(R.id.nama_dosen);
            countMember = (TextView) view.findViewById(R.id.memberCount);
            semester = (TextView) view.findViewById(R.id.semester_class);
        }

        public void bind(final Group group, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(group);
                }
            });
        }
    }

    public void animateTo(List<Group> filteredGroups) {
        applyAndAnimateRemovals(filteredGroups);
        applyAndAnimateAdditions(filteredGroups);
        applyAndAnimateMovedItems(filteredGroups);
    }

    private void applyAndAnimateRemovals(List<Group> filteredGroups) {
        for (int i = groups.size() - 1; i >= 0  ; i--) {
            final Group group = groups.get(i);
            if (!filteredGroups.contains(group)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Group> filteredGroups) {
        for (int i = 0, count = filteredGroups.size(); i < count; i++) {
            final Group group = filteredGroups.get(i);
            if (!groups.contains(group)) {
                addItem(i, group);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Group> filteredGroups) {
        for (int toPosition = filteredGroups.size() - 1; toPosition >= 0; toPosition--) {
            final Group group = filteredGroups.get(toPosition);
            final int fromPosition = groups.indexOf(group);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Group removeItem(int position) {
        final Group group = groups.remove(position);
        notifyItemRemoved(position);
        return group;
    }

    public void addItem(int position, Group group) {
        groups.add(position, group);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Group group = groups.remove(fromPosition);
        groups.add(toPosition, group);
        notifyItemMoved(fromPosition, toPosition);
    }

}