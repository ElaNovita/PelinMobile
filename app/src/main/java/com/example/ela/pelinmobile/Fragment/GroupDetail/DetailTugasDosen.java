package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.R;

import java.text.ParseException;

/**
 * Created by e on 28/05/16.
 */
public class DetailTugasDosen extends AppCompatActivity {

    String TAG = "Respon";
    TextView title, desc, attach, due;
    CustomDateFormatter cdf = new CustomDateFormatter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tugas_dosen);

        title = (TextView) findViewById(R.id.tugas_title);
        desc = (TextView) findViewById(R.id.desc);
        attach = (TextView) findViewById(R.id.attach);
        due = (TextView) findViewById(R.id.dueDate);

        getSupportActionBar().setTitle(getIntent().getStringExtra("groupTitle"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText(getIntent().getStringExtra("title"));
        desc.setText(getIntent().getStringExtra("desc"));
        attach.setText(getIntent().getStringExtra("attach"));
        try {
            due.setText(cdf.format(getIntent().getStringExtra("due")));
        } catch (ParseException e) {
            //
        }
    }
}
