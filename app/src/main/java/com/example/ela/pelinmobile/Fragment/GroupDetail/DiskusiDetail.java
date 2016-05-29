package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ela.pelinmobile.Adapter.DiskusiAdapter;
import com.example.ela.pelinmobile.Adapter.DiskusiDetailAdapter;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.DiskusiInterface;
import com.example.ela.pelinmobile.Interface.ReplyInterface;
import com.example.ela.pelinmobile.Model.DetailDiskusiModel;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.Model.ReplyModel;
import com.example.ela.pelinmobile.Model.VoteModel;
import com.example.ela.pelinmobile.R;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 29/03/16.
 */
public class DiskusiDetail extends AppCompatActivity {

    private List<DiskusiModel> detailDiskusis;
    int counter;
    String TAG = "respon", name, created, txtContent;
    RecyclerView recyclerView;
    int replySize, groupId, postId;
    ImageView sendreply, like, sender;
    boolean voted;
    TextView replyCount, senderName, createdAt, content, postReply, likeCount;
    DiskusiAdapter adapter;
    CustomDateFormatter cdf = new CustomDateFormatter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diskusi_detail);

        like = (ImageView) findViewById(R.id.like_btn);
        likeCount = (TextView) findViewById(R.id.likeCountDetail);
        replyCount = (TextView) findViewById(R.id.replyDiskusicountDetail);
        recyclerView = (RecyclerView) findViewById(R.id.detailDiskusiRv);

        senderName = (TextView) findViewById(R.id.senderDetail);
        content = (TextView) findViewById(R.id.diskusiContentDetail);
        createdAt = (TextView) findViewById(R.id.sendAtDetail);
        postReply = (TextView) findViewById(R.id.postReply);
        sendreply = (ImageView) findViewById(R.id.diskusi_send);
        sender = (ImageView) findViewById(R.id.senderImgDetail);

        senderName.setText("fa");

        groupId = getIntent().getIntExtra("groupId", 1);
        postId = getIntent().getIntExtra("postId", 1);

        ReplyInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(ReplyInterface.class);
        Call<DetailDiskusiModel> calls = service.getPostDetail(groupId, postId);
        calls.enqueue(new Callback<DetailDiskusiModel>() {
            @Override
            public void onResponse(Call<DetailDiskusiModel> call, Response<DetailDiskusiModel> response) {

                DetailDiskusiModel diskusi = response.body();
                counter = diskusi.getVotesCount();
                senderName.setText(diskusi.getUser().getName());
                String imgUrl = diskusi.getUser().getPhoto().getSmall();

                if (imgUrl == null) {
                    sender.setImageResource(R.drawable.purple1);
                } else {
                    Glide.with(getApplicationContext()).load(imgUrl).into(sender);
                }

                try {
                    createdAt.setText(cdf.format(diskusi.getCreatedAt()));
                } catch (ParseException e) {
                    //
                }
                content.setText(diskusi.getText());
                setCountText(counter);

                voted = diskusi.isVoted();

                if (voted == true) {
                    like.setBackgroundResource(R.drawable.heart);
                }

            }

            @Override
            public void onFailure(Call<DetailDiskusiModel> call, Throwable t) {

            }
        });


        loadJson();

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//
//        likeCount.setText(Integer.toString(counter));

        sendreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                ReplyModel replyModel = new ReplyModel();

                startAnim();

                txtContent = postReply.getText().toString();
                replyModel.setText(txtContent);
                postReply.setText("");

                ReplyInterface replyInterface1 = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(ReplyInterface.class);
                Call<ReplyModel> call1 = replyInterface1.postReply(groupId, postId, replyModel);
                call1.enqueue(new Callback<ReplyModel>() {
                    @Override
                    public void onResponse(Call<ReplyModel> call, Response<ReplyModel> response) {

                        loadJson();
                    }

                    @Override
                    public void onFailure(Call<ReplyModel> call, Throwable t) {
                        stopAnim();
                    }
                });

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voted = !voted;

                if (voted) {
                    counter += 1;
                    like.setBackgroundResource(R.drawable.heart);
                } else {
                    counter -= 1;
                    like.setBackgroundResource(R.drawable.heartoutline);
                }
                setCountText(counter);

                reqVote();

            }
        });


    }

    public void count(int like) {
        like += 1;
    }

    public void startAnim() {
        findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        findViewById(R.id.load).setVisibility(View.GONE);
    }

    public void loadJson() {
        ReplyInterface replyInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(ReplyInterface.class);
        Call<List<ReplyModel>> call = replyInterface.getReplys(groupId, postId);

        startAnim();

        call.enqueue(new Callback<List<ReplyModel>>() {
            @Override
            public void onResponse(Call<List<ReplyModel>> call, Response<List<ReplyModel>> response) {
                List<ReplyModel> replyModels = response.body();

                if (replyModels == null) {
                    replySize = 0;
                } else {
                    replySize = replyModels.size();
                }


                replyCount.setText(Integer.toString(replySize));

                DiskusiDetailAdapter adapter = new DiskusiDetailAdapter(replyModels, getApplicationContext());
                recyclerView.setAdapter(adapter);

                stopAnim();
            }

            @Override
            public void onFailure(Call<List<ReplyModel>> call, Throwable t) {
                stopAnim();
            }
        });
    }

    private void reqVote() {
        ReplyInterface replyInterface = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(ReplyInterface.class);
        Call<VoteModel> call = replyInterface.getVoted(groupId, postId);
        call.enqueue(new Callback<VoteModel>() {
            @Override
            public void onResponse(Call<VoteModel> call, Response<VoteModel> response) {
                VoteModel voteModel = response.body();

                Log.d(TAG, "onResponse: msg " + voteModel.getMsg());
            }

            @Override
            public void onFailure(Call<VoteModel> call, Throwable t) {

            }
        });
    }

    private void setCountText(int counter) {
        likeCount.setText(Integer.toString(counter));
    }

}
