package com.example.ela.pelinmobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.ela.pelinmobile.Model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by e on 14/04/16.
 */
public class Login extends AppCompatActivity {

    String BaseUrl = "http://pelinapi-edsproject.rhcloud.com/api/jwt";
    String TAG = "respon";
    TextView username;
    TextView password;
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

        sharedPref = getSharedPreferences(myPref, Context.MODE_PRIVATE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                startAnim();

                RequestParams params = new RequestParams();
                params.put("email", username.getText().toString());
                params.put("password", password.getText().toString());

                client.post(BaseUrl, params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {

                            String token = response.getString("token");

                            MySharedPreferences mf = new MySharedPreferences(Login.this);
                            mf.setToken(token);
                            reqMe();
                            stopAnim();

                            Intent intent = new Intent(Login.this, HomeDosen.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        stopAnim();
                        Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: " + errorResponse, throwable);
                    }
                });
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
                MySharedPreferences mf = new MySharedPreferences(getApplicationContext());
                mf.setStatus(isTeacher);
                Log.d(TAG, "onResponse: log " + isTeacher);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
