package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.MateriAdapter;
import com.example.ela.pelinmobile.Helper.CustomDateFormatter;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.MateriInterface;
import com.example.ela.pelinmobile.Model.MateriModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;
import com.example.ela.pelinmobile.UploadMateri;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateriFragment extends Fragment {

    int groupId, materiId;
    RecyclerView recyclerView;
    MateriAdapter adapter;
    Button delete;
    SwipeRefreshLayout swipeRefreshLayout;
    AVLoadingIndicatorView load;
    View inflated;
    String groupTitle;
    boolean isOwner, isTeacher;
    Bundle bundle;
    ImageView download, detail;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    int id = 1;
    CustomDateFormatter cdf = new CustomDateFormatter();


    public MateriFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");
        groupTitle = getArguments().getString("groupTitle");

        MySharedPreferences mf = new MySharedPreferences(getActivity());
        isTeacher = mf.getStatus();

        reqJson();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated =  inflater.inflate(R.layout.fragment_materi, container, false);

        delete = (Button) inflated.findViewById(R.id.delete);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.materiRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout) inflated.findViewById(R.id.swipeRefresh);
        load = (AVLoadingIndicatorView) inflated.findViewById(R.id.load);

        FloatingActionButton floatingActionButton = (FloatingActionButton) inflated.findViewById(R.id.addMateri);

        if (isTeacher) {
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            floatingActionButton.setVisibility(View.GONE);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UploadMateri.class);
                intent.putExtra("groupId", groupId);
                intent.putExtra("groupTitle", groupTitle);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startAnim();
                reqJson();
            }
        });

        startAnim();

        return inflated;
    }

    private void reqJson() {
        MateriInterface materiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MateriInterface.class);

        Call<List<MateriModel>> call = materiInterface.getMateri(groupId);
        call.enqueue(new Callback<List<MateriModel>>() {
            @Override
            public void onResponse(Call<List<MateriModel>> call, Response<List<MateriModel>> response) {
                try {
                    final List<MateriModel> materiModels = response.body();

                    adapter = new MateriAdapter(materiModels, getActivity());

                    adapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, boolean isLongClick) {
                            String url;
                            if (materiModels.get(position).getFileModels().size() != 0) {
                                //TODO why get(position) on getfilesmodel return error?
                                url = materiModels.get(position).getFileModels().get(position).getFile();
                            } else {
                                url = "";
                            }
                            bundle = new Bundle();
                            bundle.putString("url", url);
                            materiId = materiModels.get(position).getId();

                            if (isLongClick) {
                                if (isTeacher) {
                                    delete.setVisibility(View.VISIBLE);
                                    delete.setText("Delete " + materiModels.get(position).getTitle() + "?");
                                    delete.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteMateri();
                                            delete.setVisibility(View.GONE);
                                            adapter.removeItem(position);

                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), materiModels.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Intent intent = new Intent(getActivity(), MateriDetail.class);
                                intent.putExtra("title", materiModels.get(position).getTitle());
                                try {
                                    intent.putExtra("date", cdf.getTimeAgo(materiModels.get(position).getCreatedAt()));
                                } catch (ParseException e) {
                                    intent.putExtra("date", "");
                                }
                                intent.putExtra("desc", materiModels.get(position).getDescription());
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("materiId", materiModels.get(position).getId());
                                startActivity(intent);
                            }

                        }
                    });

                    stopAnim();
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(Call<List<MateriModel>> call, Throwable t) {
                Log.e("respon", "onFailure: ", t);
                stopAnim();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void deleteMateri() {
        MateriInterface materiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(MateriInterface.class);
        Call<ResponseBody> call = materiInterface.deleteMateri(groupId, materiId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                reqJson();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void startAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.GONE);
    }


}
