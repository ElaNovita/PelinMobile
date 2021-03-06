    package com.example.ela.pelinmobile;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Build;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.bumptech.glide.Glide;
    import com.example.ela.pelinmobile.Helper.MySharedPreferences;
    import com.example.ela.pelinmobile.Helper.RealPathUtil;
    import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
    import com.example.ela.pelinmobile.Interface.UserInterface;
    import com.example.ela.pelinmobile.Model.User;

    import java.io.File;

    import okhttp3.MediaType;
    import okhttp3.MultipartBody;
    import okhttp3.RequestBody;
    import okhttp3.ResponseBody;
    import retrofit2.Call;
    import retrofit2.Callback;
    import retrofit2.Response;

    /**
     * Created by e on 2/05/16.
     */
    public class EditProfile extends AppCompatActivity {

        EditText name, email, phone, passowrd;
        TextView nm_file;
        Button save, select;
        ImageView image;
        MultipartBody.Part requestFileBody;
        RequestBody reqName, reqEmail, reqPhone, reqPass;
        private static final int PICKFILE_RESULT_CODE = 1;
        String TAG = "respon";


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_profile);


            nm_file = (TextView) findViewById(R.id.nm_file);
            name = (EditText) findViewById(R.id.name);
            email = (EditText) findViewById(R.id.mail);
            phone = (EditText) findViewById(R.id.phone);
            passowrd = (EditText) findViewById(R.id.password);
            save = (Button) findViewById(R.id.save);
            select = (Button) findViewById(R.id.select_img);
            image = (ImageView) findViewById(R.id.edt_img);

            name.setText(getIntent().getStringExtra("username"));
            email.setText(getIntent().getStringExtra("email"));
            phone.setText(getIntent().getStringExtra("phone"));
            passowrd.setText(getIntent().getStringExtra("pass"));
            String img = getIntent().getStringExtra("img");

            if (img.matches("")) {
                image.setImageResource(R.drawable.purple1);
            } else {
                Glide.with(this).load(getIntent().getStringExtra("img")).into(image);
            }

            Log.d(TAG, "onCreate: name " + name.getText() + email.getText() + phone.getText());

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICKFILE_RESULT_CODE);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String _name = name.getText().toString();
                    String _email = email.getText().toString();
                    String _phone = phone.getText().toString();
                    String _pass = passowrd.getText().toString();

                    reqName = RequestBody.create(MediaType.parse("multipart/form-data"), _name);
                    reqEmail = RequestBody.create(MediaType.parse("multipart/form-data"), _email);
                    reqPhone = RequestBody.create(MediaType.parse("multipart/form-data"), _phone);
                    reqPass = RequestBody.create(MediaType.parse("multipart/form-data"), _pass);

                    reqJson();

                    Log.d("respon", "onClick: " + requestFileBody + reqName);

                    Intent intent = new Intent(getApplicationContext(), HomeDosen.class);
                    startActivity(intent);
                }
            });

        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case PICKFILE_RESULT_CODE:
                    if (resultCode == RESULT_OK) {

                        String realPath;

                        if (Build.VERSION.SDK_INT < 11) {
                            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
                        } else if (Build.VERSION.SDK_INT < 19) {
                            realPath = RealPathUtil.getRealPathFromURI_APIto18(this, data.getData());
                        } else {
                            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                        }
                        nm_file.setText(realPath);

                        Uri uri = data.getData();

                        File file = new File(realPath);

                        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), file);
                        requestFileBody = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

                        Log.d("respon", "onActivityResult: " + realPath);

                    }
                    break;
            }
        }

        public void reqJson() {

            UserInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(UserInterface.class);
            Call<User> call = service.editUser(reqName, reqPhone, reqEmail, reqPass, requestFileBody);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    MySharedPreferences mf = new MySharedPreferences(getApplicationContext());

                    mf.setUsername(response.body().getName());
                    mf.setUserImage(response.body().getPhoto().getMedium());

                    Toast.makeText(getApplicationContext(), "Profil Berhasil diubah", Toast.LENGTH_LONG).show();

                    finish();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Profil Gagal diubah", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
