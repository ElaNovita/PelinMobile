package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ela.pelinmobile.Adapter.ConfirmAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e on 9/04/16.
 */
public class ConfirmMember extends AppCompatActivity {

    private List<Confirm> confirms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_member);
        initdata();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.confirmRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ConfirmAdapter adapter = new ConfirmAdapter(confirms);
        recyclerView.setAdapter(adapter);
    }

    public class Confirm {
        public String name, nim;

        public Confirm(String nim, String name) {
            this.nim = nim;
            this.name = name;
        }
    }

    private void initdata() {
        confirms = new ArrayList<>();
        confirms.add(new Confirm("1210520070", "Ela Novita"));
        confirms.add(new Confirm("1210520070", "Ela Novita"));
        confirms.add(new Confirm("1210510006", "Bahrul Hidayat"));
        confirms.add(new Confirm("1210510006", "Bahrul Hidayat"));
        confirms.add(new Confirm("1210510006", "Bahrul Hidayat"));
        confirms.add(new Confirm("1210510006", "Bahrul Hidayat"));

    }
}
