package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ela.pelinmobile.Adapter.DiskusiDetailAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 29/03/16.
 */
public class DiskusiDetail extends AppCompatActivity {

    private List<DetailDiskusi> detailDiskusis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diskusi_detail);
        initData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.detailDiskusiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        DiskusiDetailAdapter adapter = new DiskusiDetailAdapter(detailDiskusis);
        recyclerView.setAdapter(adapter);
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
