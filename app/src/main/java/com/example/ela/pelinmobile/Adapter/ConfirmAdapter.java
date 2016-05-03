package com.example.ela.pelinmobile.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Fragment.GroupDetail.ConfirmMember;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.HomeDosen;
import com.example.ela.pelinmobile.Interface.RequestInterface;
import com.example.ela.pelinmobile.Model.ApproveModel;
import com.example.ela.pelinmobile.Model.RequestModel;
import com.example.ela.pelinmobile.Model.User;
import com.example.ela.pelinmobile.R;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 9/04/16.
 */
public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.ViewHolder> {
    private List<RequestModel> users;
    Context context;
    int groupId, reqId;

    public ConfirmAdapter(List<RequestModel> users, Context context, int groupId) {
        this.users = users;
        this.context = context;
        this.groupId = groupId;
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(users.get(position).getUser().getName());
        holder.id.setText(Integer.toString(users.get(position).getId()));
        holder.nim.setText(users.get(position).getUser().getStudent().getNim());
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reject(users.get(position).getId());
                removeItem(position);
            }
        });
        holder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reqJson(groupId, users.get(position).getId());
                removeItem(position);
            }
        });
    }




    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, nim, id;
        ImageButton confirm, reject;

        public ViewHolder(final View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.confimr_nm);
            nim = (TextView) itemView.findViewById(R.id.confirm_nim);
            id = (TextView) itemView.findViewById(R.id.itemPosition);
            confirm = (ImageButton) itemView.findViewById(R.id.confirm);
            reject = (ImageButton) itemView.findViewById(R.id.reject);

        }


    }

    public void removeItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public void addToGroup(int position) {

    }

    public void reqJson(int groupId, int reqId) {
        RequestInterface service = new RetrofitBuilder(context).getRetrofit().create(RequestInterface.class);
        Call<ApproveModel> call = service.confirmUser(groupId, reqId);
        call.enqueue(new Callback<ApproveModel>() {
            @Override
            public void onResponse(Call<ApproveModel> call, Response<ApproveModel> response) {
                Toast.makeText(context, "sukses", Toast.LENGTH_SHORT).show();

                Log.d("respon", "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<ApproveModel> call, Throwable t) {

            }
        });
    }

    public void reject(int position) {
        RequestInterface service = new RetrofitBuilder(context).getRetrofit().create(RequestInterface.class);
        Call<ResponseBody> call = service.decline(4, position);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("respon", "onResponse: reject " + response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
