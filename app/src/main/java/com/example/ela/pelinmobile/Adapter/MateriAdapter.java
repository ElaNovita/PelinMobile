package com.example.ela.pelinmobile.Adapter;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriDetail;
import com.example.ela.pelinmobile.Fragment.GroupDetail.MateriFragment;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ela on 18/03/16.
 */
public class MateriAdapter extends RecyclerView.Adapter<MateriAdapter.ViewHolder> {

    List<MateriModel> materiModels;
    CustomDateFormatter cdf = new CustomDateFormatter();
    Context context;
    private NotificationManager notify;
    private NotificationCompat.Builder builder;

    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MateriAdapter(List<MateriModel> materiModels, Context context) {
        this.materiModels = materiModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return materiModels.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.materi_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(materiModels.get(position).getTitle());
        try {
            holder.time.setText(cdf.format(materiModels.get(position).getCreatedAt()));
        } catch (ParseException e) {

        }

    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time;
        ImageView download, detail;

        public ViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.materiTitle);
            time = (TextView) itemView.findViewById(R.id.posted);
            download = (ImageView) itemView.findViewById(R.id.downloadmateri);
            detail = (ImageView) itemView.findViewById(R.id.detailMateri);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.OnItemClick(itemView, getLayoutPosition(), false);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.OnItemClick(itemView, getLayoutPosition(), true);
                    }
                    return true;
                }
            });

            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = materiModels.get(7).getFileModels().get(5).getFile();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });

            detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MateriDetail.class);
                    intent.putExtra("title", materiModels.get(getLayoutPosition()).getTitle());
                    intent.putExtra("date", materiModels.get(getLayoutPosition()).getCreatedAt());
                    intent.putExtra("desc", materiModels.get(getLayoutPosition()).getDescription());
//                    intent.putExtra("url")
                    Log.d("respon", "onClick: desc " + materiModels.get(getLayoutPosition()).getDescription());
                    context.startActivity(intent);
                }
            });

        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View view, int position, boolean isLongClick);
    }

    public void removeItem(int position) {
        materiModels.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
