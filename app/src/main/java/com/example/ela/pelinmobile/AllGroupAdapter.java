package com.example.ela.pelinmobile;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.Model.JoinModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 7/04/16.
 */
public class AllGroupAdapter extends RecyclerView.Adapter<AllGroupAdapter.ViewHolder> {

    List<GroupModel> groups;
    OnItemClickListener listener;
    Context context;

    public AllGroupAdapter(List<GroupModel> groups, Context context, OnItemClickListener listener) {
        this.groups = new ArrayList<>(groups);
        this.listener = listener;
        this.context = context;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bind(groups.get(position), listener);
        holder.groupTitle.setText(groups.get(position).getTitle());
        holder.dosenName.setText(groups.get(position).getTeacher().getName());
        holder.countMember.setText(Integer.toString(groups.get(position).getMember()));
//        holder.semester.setText(groups.get(position).get);

        if (groups.get(position).isJoined()) {
            joined(holder.join);

        } else if (groups.get(position).isPending()) {
            waitForApproval(holder.join);
        }

        holder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groups.get(position).isJoined()) {
                    detail(groups.get(position).getId());
                } else if (groups.get(position).isPending()) {
                    cancel(position);
                    cancelReq(holder.join);
                } else {
                    join(groups.get(position).getId());
                    waitForApproval(holder.join);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void OnItemClick(GroupModel group);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupTitle, dosenName, countMember, semester;
        Button join;

        ViewHolder(View view) {
            super(view);

            groupTitle = (TextView) view.findViewById(R.id.nama_group);
            dosenName = (TextView) view.findViewById(R.id.nama_dosen);
            countMember = (TextView) view.findViewById(R.id.memberCount);
            semester = (TextView) view.findViewById(R.id.semester_class);
            join = (Button) view.findViewById(R.id.join);

        }

        public void bind(final GroupModel group, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(group);
                }
            });
        }
    }

    public void animateTo(List<GroupModel> filteredGroups) {
        applyAndAnimateRemovals(filteredGroups);
        applyAndAnimateAdditions(filteredGroups);
        applyAndAnimateMovedItems(filteredGroups);
    }

    private void applyAndAnimateRemovals(List<GroupModel> filteredGroups) {
        for (int i = groups.size() - 1; i >= 0  ; i--) {
            final GroupModel group = groups.get(i);
            if (!filteredGroups.contains(group)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<GroupModel> filteredGroups) {
        for (int i = 0, count = filteredGroups.size(); i < count; i++) {
            final GroupModel group = filteredGroups.get(i);
            if (!groups.contains(group)) {
                addItem(i, group);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<GroupModel> filteredGroups) {
        for (int toPosition = filteredGroups.size() - 1; toPosition >= 0; toPosition--) {
            final GroupModel group = filteredGroups.get(toPosition);
            final int fromPosition = groups.indexOf(group);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public GroupModel removeItem(int position) {
        final GroupModel group = groups.remove(position);
        notifyItemRemoved(position);
        return group;
    }

    public void addItem(int position, GroupModel group) {
        groups.add(position, group);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final GroupModel group = groups.remove(fromPosition);
        groups.add(toPosition, group);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void join(int id) {

        GroupInterface service = new RetrofitBuilder(context).getRetrofit().create(GroupInterface.class);
        Call<JoinModel> call = service.join(id);
        call.enqueue(new Callback<JoinModel>() {
            @Override
            public void onResponse(Call<JoinModel> call, Response<JoinModel> response) {
                Log.d("respon", "onResponse: joined " + response.code());
            }

            @Override
            public void onFailure(Call<JoinModel> call, Throwable t) {

            }
        });
    }

    private void cancel(int id) {

    }

    private void detail(int id) {
        Intent intent = new Intent(context, GroupDetail.class);
        intent.putExtra("groupId", id);
        context.startActivity(intent);
    }

    private void joined(Button join) {
        Drawable joined = context.getResources().getDrawable(R.drawable.ic_remove_red_eye_white_24dp);
        join.setText("Detail");
        join.setTextColor(context.getResources().getColor(R.color.white));
        join.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        join.setCompoundDrawables(null, null, joined, null);
    }

    private void waitForApproval(Button join) {
        Drawable joined = context.getResources().getDrawable(R.drawable.ic_remove_red_eye_white_24dp);
        join.setText("Batal");
        join.setTextColor(context.getResources().getColor(R.color.white));
        join.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        join.setCompoundDrawables(null, null, joined, null);
    }

    private void cancelReq(Button join) {
        Drawable joined = context.getResources().getDrawable(R.drawable.ic_add_white_24dp);
        join.setText("Join");
        join.setTextColor(context.getResources().getColor(R.color.white));
        join.setBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
        join.setCompoundDrawables(null, null, joined, null);
    }
}