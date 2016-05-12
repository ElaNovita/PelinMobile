package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.TugasAdapter;
import com.example.ela.pelinmobile.AddTugas;
import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.Helper.MySharedPreferences;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TugasFragment extends Fragment {

    private List<TugasModel> tugases;
    RecyclerView recyclerView;
    Bundle bundle;
    int groupId;
    Button kick;
    TugasAdapter adapter;
    AVLoadingIndicatorView load;
    SwipeRefreshLayout swipeRefreshLayout;
    View inflated;
    String attachment;
    boolean isOwner, isTeacher;

    public TugasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");
        isOwner = getArguments().getBoolean("owner");

        MySharedPreferences mf = new MySharedPreferences(getActivity());
        isTeacher = mf.getStatus();

        bundle = new Bundle();
        bundle.putInt("groupId", groupId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_tugas, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.tugastRv);
        kick = (Button) inflated.findViewById(R.id.delete);
        load = (AVLoadingIndicatorView) inflated.findViewById(R.id.load);
        swipeRefreshLayout = (SwipeRefreshLayout) inflated.findViewById(R.id.swipeRefresh);

        startAnim();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startAnim();
                reqJson();
            }
        });

        load.setVisibility(View.VISIBLE);

        reqJson();

        FloatingActionButton floatingActionButton = (FloatingActionButton) inflated.findViewById(R.id.addTugas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (isTeacher) {
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            floatingActionButton.setVisibility(View.GONE);
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTugas.class);
                intent.putExtra("groupId", groupId);
                startActivity(intent);
            }
        });

        return inflated;
    }

    private void reqJson() {
        TugasInterface tugasInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(TugasInterface.class);
        Call<List<TugasModel>> call = tugasInterface.getTugas(groupId);
        call.enqueue(new Callback<List<TugasModel>>() {
            @Override
            public void onResponse(Call<List<TugasModel>> call, Response<List<TugasModel>> response) {
                try {
                    final List<TugasModel> tugasModels = response.body();

                    load.setVisibility(View.GONE);

                    adapter = new TugasAdapter(tugasModels, getActivity(), new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, boolean isLongClick) {

                            attachment = "attach";
                            String due = tugasModels.get(position).getDueDate();
                            try {
                                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").parse(due);
                            } catch (ParseException e) {

                            }

                            if (isLongClick) {

                                if (isTeacher) {
                                    kick.setVisibility(View.VISIBLE);
                                    kick.setText("Delete " + tugasModels.get(position).getTitle() + "?");
                                    kick.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteTugas(tugasModels.get(position).getId());
                                            kick.setVisibility(View.GONE);
                                            adapter.removeItem(position);
                                        }
                                    });
                                } else {
                                    Toast.makeText(getActivity(), tugasModels.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                if (isTeacher) {
                                    Intent intent = new Intent(getActivity(), ListTugas.class);
                                    intent.putExtra("groupId", groupId);
                                    intent.putExtra("tugasId", tugasModels.get(position).getId());
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(getActivity(), AssigntDetail.class);
                                    intent.putExtra("groupId", groupId);
                                    intent.putExtra("tugasId", tugasModels.get(position).getId());
                                    intent.putExtra("title", tugasModels.get(position).getTitle());
                                    intent.putExtra("content", tugasModels.get(position).getDescription());
                                    intent.putExtra("file", attachment);
                                    startActivity(intent);
                                }
                            }


                        }
                    });

                    stopAnim();
                    swipeRefreshLayout.setRefreshing(false);
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: ", e);
                }
            }

            @Override
            public void onFailure(Call<List<TugasModel>> call, Throwable t) {
                Log.e("respon", "onFailure: ", t);
                load.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                stopAnim();
            }
        });
    }

    private void deleteTugas(int tugasId) {
        TugasInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(TugasInterface.class);
        Call<ResponseBody> call = service.deleteTugas(groupId, tugasId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                reqJson();
                Log.d("respon", "onResponse: " + response.code());
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
