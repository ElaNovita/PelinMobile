package com.example.ela.pelinmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by e on 1/04/16.
 */
public class AddTugas extends AppCompatActivity {

    TextView TextuploadMateri;
    Button btnSendMateri;
    RelativeLayout upMateri;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_materi);

        TextuploadMateri = (TextView) findViewById(R.id.uploadMateri);
        btnSendMateri = (Button) findViewById(R.id.sendMateri);
        upMateri = (RelativeLayout) findViewById(R.id.upMateri);

        upMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_RESULT_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    String FilePath = data.getData().getLastPathSegment();
                    TextuploadMateri.setText(FilePath);
                    btnSendMateri.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
