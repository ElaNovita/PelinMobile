package com.example.ela.pelinmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MyInterface;
import com.example.ela.pelinmobile.Model.LoginModel;
import com.example.ela.pelinmobile.Model.TokenModel;
import com.example.ela.pelinmobile.Model.User;
import com.example.ela.pelinmobile.Service.InstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by e on 14/04/16.
 */
public class Login extends AppCompatActivity {

    String TAG = "respon";
    TextView username, password, register;
    Button login;
    public static final String myPref = "myPrefs";
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        username = (TextView) findViewById(R.id.username_login);
        password = (TextView) findViewById(R.id.password_login);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.reg);

        sharedPref = getSharedPreferences(myPref, Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnim();

                LoginModel model = new LoginModel();
                model.setUsername(username.getText().toString());
                model.setPassword(password.getText().toString());

                com.example.ela.pelinmobile.Interface.Login service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(com.example.ela.pelinmobile.Interface.Login.class);
                Call<TokenModel> call = service.login(model);
                call.enqueue(new Callback<TokenModel>() {
                    @Override
                    public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                        try {
                            if (response.code() == 200) {
                                TokenModel loginModel = response.body();
                                Log.d(TAG, "onResponse: code " + response.code());
                                String token = loginModel.getToken();
                                MySharedPreferences mf = new MySharedPreferences(Login.this);
                                mf.setToken(token);
                                reqMe();
                                stopAnim();

//                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                Log.d(TAG, "onResponse: " + sp.getString("firebaseToken", "token kosong"));

//                                if (FirebaseInstanceId.getInstance().getToken() != null) {
                                RetrofitBuilder rb = new RetrofitBuilder(getApplicationContext());
                                rb.sendTokenToServer(FirebaseInstanceId.getInstance().getToken());

//                                }

                                Intent intent = new Intent(Login.this, HomeDosen.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Login Gagal 2, Coba Lagi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TokenModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Login Gagal, Coba Lagi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }

    public void startAnim() {
        findViewById(R.id.wrap).setVisibility(View.VISIBLE);
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    public void stopAnim() {
        findViewById(R.id.wrap).setVisibility(View.GONE);
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    private void reqMe() {
        MyInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(MyInterface.class);
        Call<User> call = service.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                boolean isTeacher = response.body().isTeacher();
                String username = response.body().getName();
                String imageUrl = response.body().getPhoto().getMedium();

                MySharedPreferences mf = new MySharedPreferences(getApplicationContext());
                mf.setStatus(isTeacher);
                mf.setUsername(username);
                mf.setUserImage(imageUrl);


                Log.d(TAG, "onResponse: log " + isTeacher);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
