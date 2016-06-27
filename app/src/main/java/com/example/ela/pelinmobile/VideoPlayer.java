package com.example.ela.pelinmobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ela.pelinmobile.Helper.Config;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by e on 31/05/16.
 */
public class VideoPlayer extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String title, user, desc, created, youtubeId;
    ArrayList<String> categoris;
    CustomDateFormatter cdf = new CustomDateFormatter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.video_player);

        title = getIntent().getStringExtra("title");
        user = getIntent().getStringExtra("user");
        desc = getIntent().getStringExtra("desc");
        youtubeId = getIntent().getStringExtra("youtubeId");
        created = getIntent().getStringExtra("created");
        categoris = getIntent().getStringArrayListExtra("tags");

        TextView _title = (TextView) findViewById(R.id.titles);
        TextView _user = (TextView) findViewById(R.id.user);
        TextView _desc = (TextView) findViewById(R.id.desc);
        TextView date = (TextView) findViewById(R.id.date);
        LinearLayout tags = (LinearLayout) findViewById(R.id.tags);

        _title.setText(title);
        _user.setText(user);
        _desc.setText(desc);
        addTxt(tags, categoris);

        try {
            date.setText(cdf.format(created));
        } catch (ParseException e) {
            //
        }

//        videoView = (VideoView) findViewById(R.id.video);
//        videoView.setVideoURI(Uri.parse(url));
//        videoView.setMediaController(new MediaController(this));
//        videoView.requestFocus();
//
//        MediaController vidController = new MediaController(getApplicationContext());
//        vidController.setAnchorView(videoView);
//        videoView.setMediaController(vidController);
//
//        videoView.start();

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(Config.YOUTUBE_API_KEY, VideoPlayer.this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo(youtubeId);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error));
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, VideoPlayer.this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
    }

    public void addTxt(LinearLayout linearLayout, ArrayList<String> tags) {
        TextView[] txt = new TextView[3];
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 4, 0);

        for (int i = 0; i < tags.size(); i++) {
            txt[i] = new TextView(getApplicationContext());
            txt[i].setText(tags.get(i));
            txt[i].setTextColor(getApplicationContext().getResources().getColor(R.color.tag_text));
            txt[i].setBackgroundColor(getApplicationContext().getResources().getColor(R.color.tag));
            txt[i].setPadding(5, 8, 8, 5);
            txt[i].setLayoutParams(llp);
            linearLayout.addView(txt[i]);
        }


    }
}
