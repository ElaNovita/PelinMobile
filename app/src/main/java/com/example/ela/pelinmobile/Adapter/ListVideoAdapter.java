package com.example.ela.pelinmobile.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Model.ListVidModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.text.ParseException;
import java.util.List;

/**
 * Created by e on 15/06/16.
 */
public class ListVideoAdapter extends RecyclerView.Adapter<ListVideoAdapter.ViewHolder> {

    private Context context;
    private List<ListVidModel> vidModelList;
    private static OnItemClickListener listener;
    CustomDateFormatter cdf = new CustomDateFormatter();

    public ListVideoAdapter(Context context, List<ListVidModel> vidModelList) {
        this.context = context;
        this.vidModelList = vidModelList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return vidModelList == null ? 0 : vidModelList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_video_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.user.setText(vidModelList.get(position).getUser().getName());
        Log.d("respon", "onBindViewHolder: " + vidModelList.get(position).getUser().getTeacher().getNik());
        holder.title.setText(vidModelList.get(position).getTitle());
        addTxt(holder.linearLayout, position);
        Glide.with(context).load("http://img.youtube.com/vi/" + vidModelList.get(position).getYoutubeId() +"/0.jpg")
                .into(holder.thumbnail);
        try {
            holder.date.setText(cdf.format(vidModelList.get(position).getCreatedAt()));
        } catch (ParseException e) {
            //
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView title, user, date;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            thumbnail = (ImageView) itemView.findViewById(R.id.youtube_view);
            title = (TextView) itemView.findViewById(R.id.titles);
            user = (TextView) itemView.findViewById(R.id.user);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.tags);
            date = (TextView) itemView.findViewById(R.id.created);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, getLayoutPosition(), false);
                    }
                }
            });


        }
    }

    public void addTxt(LinearLayout linearLayout, int position) {
        TextView[] txt = new TextView[3];
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 4, 0);

        for (int i = 0; i < vidModelList.get(position).getCategory().size(); i++) {
            txt[i] = new TextView(context);
            txt[i].setText(vidModelList.get(position).getCategory().get(i));
            txt[i].setTextColor(context.getResources().getColor(R.color.tag_text));
            txt[i].setBackgroundColor(context.getResources().getColor(R.color.tag));
            txt[i].setPadding(5, 8, 8, 5);
            txt[i].setLayoutParams(llp);
            linearLayout.addView(txt[i]);
        }


    }

}
