package com.example.ela.pelinmobile.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ela.pelinmobile.Adapter.AssigntListAdapter;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.AssigntDetail;
import com.example.ela.pelinmobile.Fragment.GroupDetail.ListTugas;
import com.example.ela.pelinmobile.Fragment.GroupDetail.PassedAssignment;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.FragmentComunicator;
import com.example.ela.pelinmobile.Interface.TugasInterface;
import com.example.ela.pelinmobile.Model.MyAssignment;
import com.example.ela.pelinmobile.Model.TugasModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssigntFragment extends Fragment {

    private List<TugasModel> tugasModels;
    String TAG = "respon", attachment;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    View inflated;
    TextView failed;
    private FragmentComunicator comunicator;


    public AssigntFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        context = getActivity();
        comunicator = (FragmentComunicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_assignt, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.assigntRv);
        refreshLayout = (SwipeRefreshLayout) inflated.findViewById(R.id.swipeRefresh);
        failed = (TextView) inflated.findViewById(R.id.failed);

        startAnim();

        reqJson();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqJson();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        return inflated;
    }

    public void startAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.GONE);
    }

    public void reqJson() {
        TugasInterface tugasInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(TugasInterface.class);
        Call<List<MyAssignment>> call = tugasInterface.getAllTugas();
        call.enqueue(new Callback<List<MyAssignment>>() {
            @Override
            public void onResponse(Call<List<MyAssignment>> call, Response<List<MyAssignment>> response) {
                final List<MyAssignment> tugasModels = response.body();


                ArrayList<Integer> counter = new ArrayList<Integer>();



                for (MyAssignment tugas : tugasModels) {
                    if (!tugas.isPassed()) {
                        counter.add(tugas.getId());
                    }
                }

                comunicator.sendDataToActivity(counter.size());

                AssigntListAdapter adapter = new AssigntListAdapter(tugasModels, getActivity(), new AssigntListAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(MyAssignment tugasModel, int position) {

                        boolean isSubmitted = tugasModels.get(position).isSubmitted();
                        boolean isPassed = tugasModels.get(position).isPassed();
                        attachment = tugasModels.get(position).getFile();

                        //TODO if server has been fixed, delete isPassed
                        if (!isPassed) {
                            if (!isSubmitted) {
                                Intent intent = new Intent(getActivity(), AssigntDetail.class);
                                intent.putExtra("groupId", tugasModels.get(position).getGroup().getId());
                                intent.putExtra("tugasId", tugasModels.get(position).getId());
                                intent.putExtra("title", tugasModels.get(position).getTitle());
                                intent.putExtra("content", tugasModels.get(position).getDescription());
                                intent.putExtra("due", tugasModels.get(position).getDueDate());
                                intent.putExtra("isPassed", false);
                                if (attachment == null) {
                                    attachment = "";
                                } else {
                                    intent.putExtra("file", attachment);
                                }
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), PassedAssignment.class);
                                intent.putExtra("groupId", tugasModels.get(position).getGroup().getId());
                                intent.putExtra("tugasId", tugasModels.get(position).getId());
                                intent.putExtra("title", tugasModels.get(position).getTitle());
                                intent.putExtra("content", tugasModels.get(position).getDescription());
                                intent.putExtra("due", tugasModels.get(position).getDueDate());
                                intent.putExtra("isPassed", false);
                                if (attachment == null) {
                                    attachment = "";
                                } else {
                                    intent.putExtra("file", attachment);
                                }
                                startActivity(intent);
                            }
                        } else {
                            if (!isSubmitted) {
                                Intent intent = new Intent(getActivity(), PassedAssignment.class);
                                intent.putExtra("groupId", tugasModels.get(position).getGroup().getId());
                                intent.putExtra("tugasId", tugasModels.get(position).getId());
                                intent.putExtra("title", tugasModels.get(position).getTitle());
                                intent.putExtra("content", tugasModels.get(position).getDescription());
                                intent.putExtra("due", tugasModels.get(position).getDueDate());
                                intent.putExtra("isPassed", true);
                                if (attachment == null) {
                                    attachment = "";
                                } else {
                                    intent.putExtra("file", attachment);
                                }
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), PassedAssignment.class);
                                intent.putExtra("groupId", tugasModels.get(position).getGroup().getId());
                                intent.putExtra("tugasId", tugasModels.get(position).getId());
                                intent.putExtra("title", tugasModels.get(position).getTitle());
                                intent.putExtra("content", tugasModels.get(position).getDescription());
                                intent.putExtra("due", tugasModels.get(position).getDueDate());
                                intent.putExtra("isPassed", true);
                                if (attachment == null) {
                                    attachment = "";
                                } else {
                                    intent.putExtra("file", attachment);
                                }
                                startActivity(intent);
                            }
                        }


                    }
                });

                recyclerView.setAdapter(adapter);

                refreshLayout.setRefreshing(false);
                failed.setVisibility(View.GONE);
                stopAnim();

            }

            @Override
            public void onFailure(Call<List<MyAssignment>> call, Throwable t) {
                Log.d(TAG, "onFailure: ", t);

                refreshLayout.setRefreshing(false);
                failed.setVisibility(View.VISIBLE);
                stopAnim();
            }
        });
    }

}
