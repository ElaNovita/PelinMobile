package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.ConfirmAdapter;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

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
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.confirmRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ConfirmAdapter adapter = new ConfirmAdapter(confirms, getApplicationContext());
        recyclerView.setAdapter(adapter);

    }

    public class Confirm {
        public String name, nim, id;

        public Confirm(String nim, String name, int id) {
            this.nim = nim;
            this.name = name;
        }
    }

    private void initdata() {
        confirms = new ArrayList<>();
        confirms.add(new Confirm("1210520070", "Novita", 1));
        confirms.add(new Confirm("1210520070", "Dayat", 2));
        confirms.add(new Confirm("1210520070", "Bahrul", 3));
        confirms.add(new Confirm("1210520070", "Ela ", 4));
        confirms.add(new Confirm("1210520070", "Lestari", 5));
        confirms.add(new Confirm("1210520070", "Hidayat", 6));

    }


}
