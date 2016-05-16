package com.example.ela.pelinmobile.Fragment.GroupDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.R;

/**
 * Created by e on 16/05/16.
 */
public class PassedAssignment extends AppCompatActivity {
    LinearLayout ln;
    TextView status, desc, submittedFile, timer;
    Button send;
    boolean isPassed, isSubmitted ;
    ProgressBar progressBar;
    int groupId, tugasId;
    String attach, dueDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passed_assignment);

        isPassed = getIntent().getBooleanExtra("isPassed", false);
        isSubmitted = getIntent().getBooleanExtra("isSubmitted", false);
        groupId = getIntent().getIntExtra("groupId", 0);
        tugasId = getIntent().getIntExtra("tugasId", 0);
        attach = getIntent().getStringExtra("file");
        dueDate = getIntent().getStringExtra("due");
        final String _title = getIntent().getStringExtra("title");
        final String _content = getIntent().getStringExtra("content");

        ln = (LinearLayout) findViewById(R.id.ln);
        status = (TextView) findViewById(R.id.status);
        desc = (TextView) findViewById(R.id.desc);
        submittedFile = (TextView) findViewById(R.id.submittedAttach);
        send = (Button) findViewById(R.id.btnSend);
        timer = (TextView) findViewById(R.id.timer);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        if (isPassed) {
            send.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            if (isSubmitted) {
                timer.setText("Time is Over");
            } else {
                ln.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
                status.setText("Anda Tidak Mengumpulkan Tugas");
                desc.setVisibility(View.GONE);
                submittedFile.setVisibility(View.GONE);
            }

        } else {
            send.setVisibility(View.VISIBLE);
            timer.setText("Time remaining");
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AssigntDetail.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("tugasId", tugasId);
                intent.putExtra("title", _title);
                intent.putExtra("content", _content);
                intent.putExtra("due", dueDate);
                if (attach == null) {
                    attach = "";
                } else {
                    intent.putExtra("file", attach);
                }
                startActivity(intent);
            }
        });
    }
}
