package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Model.Video;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.List;

/**
 * Created by e on 31/05/16.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<Video> videos;
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public VideoAdapter(Context context, List<Video> videos) {
        this.videos = videos;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.titles.setText(video.getName());

        Glide.with(context).load(video.getThumbnail()).into(holder.thumbnail);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titles;
        public ImageView thumbnail;
        public Button detail;

        public ViewHolder(View itemView) {
            super(itemView);

            titles = (TextView) itemView.findViewById(R.id.titles);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getLayoutPosition(), false);
                    } else {
                        Toast.makeText(context, "salah", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}
