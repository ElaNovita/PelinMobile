package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;

/**
 * Created by e on 30/03/16.
 */
public class AssigntDetail extends AppCompatActivity {
    TextView timer;
    ImageView uploadImg;
    TextView namaFile;
    Button assigntSend;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assignt_detail);
        timer = (TextView) findViewById(R.id.timer);
        uploadImg = (ImageView) findViewById(R.id.uploadImg);
        namaFile = (TextView) findViewById(R.id.nmFile);
        assigntSend = (Button) findViewById(R.id.assigntUpload);

        countDownTimer.start();
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });

        assigntSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeDosen.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Tugas Anda Sudah Dikirim", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getLastPathSegment();
                    namaFile.setText(FilePath);
                    assigntSend.setVisibility(View.VISIBLE);
                }
                break;
        }
    }



    CountDownTimer countDownTimer = new CountDownTimer(300000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText("Time: " + millisUntilFinished / 1000);
        }

        @Override
        public void onFinish() {
            timer.setText("done");
        }
    };


}
