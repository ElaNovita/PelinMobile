package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ela.pelinmobile.Adapter.DiskusiDetailAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by e on 29/03/16.
 */
public class DiskusiDetail extends AppCompatActivity {

    private List<DetailDiskusi> detailDiskusis;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diskusi_detail);
        initData();
        final ImageView like = (ImageView) findViewById(R.id.like_btn);
        ImageView send = (ImageView) findViewById(R.id.diskusi_send);
        final TextView likeCount = (TextView) findViewById(R.id.likeCountDetail);
        final TextView replyCount = (TextView) findViewById(R.id.replyDiskusicountDetail);
        replyCount.setText(Integer.toString(detailDiskusis.size()));
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detailDiskusiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DiskusiDetailAdapter adapter = new DiskusiDetailAdapter(detailDiskusis);
        recyclerView.setAdapter(adapter);
        likeCount.setText(Integer.toString(counter));

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like.setBackgroundResource(R.drawable.heart);
//                count(counter);
                likeCount.setText("1");
            }
        });
    }

    public class DetailDiskusi {
        public String name, content, time;
        public int img;

        public DetailDiskusi(String name, String content, String time, int img) {
            this.name = name;
            this.content = content;
            this.time = time;
            this.img = img;
        }
    }

    public void count(int like) {
        like += 1;
    }

    private void initData() {
        detailDiskusis = new ArrayList<>();
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "What I have now is a ListView of TextView elements. each TextView element displays a text", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "What I have now is a ListView of TextView elements. each TextView element displays a text", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));
        detailDiskusis.add(new DetailDiskusi("Akashi Seijuro", "Know Your Place", "10.23 AM",  R.drawable.sei));

    }



}
