package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.DiskusiAdapter;
import com.example.ela.pelinmobile.Fragment.CreatePost;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.DiskusiInterface;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiskusiFragment extends Fragment {

    private List<DiskusiModel> diskusis;

    public static final String TAG = "respon";

    RecyclerView recyclerView;
    int groupId;
    Bundle bundle, bundleObj;
    ArrayList<Integer> postId;
    SwipeRefreshLayout swipeRefreshLayout;
    AVLoadingIndicatorView load;
    View inflated;
    Button kick;
    TextView fail;
    DiskusiAdapter adapter;

    public DiskusiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");

        reqJson();

        bundle = new Bundle();

        bundle.putInt("groupId", groupId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflated = inflater.inflate(R.layout.fragment_diskusi, container, false);

        startAnim();

        recyclerView = (RecyclerView) inflated.findViewById(R.id.dRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout = (SwipeRefreshLayout) inflated.findViewById(R.id.swipeRefresh);
        kick = (Button) inflated.findViewById(R.id.kick);
        fail = (TextView) inflated.findViewById(R.id.failed);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.diskusi);

        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addDiskusi);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startAnim();
                reqJson();
            }
        });

        return inflated;
    }

    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreatePost createPost = CreatePost.newInstance("Enter Post");
        createPost.show(fragmentManager, "Enter Post");
        createPost.setArguments(bundle);
    }

    private void reqJson() {
        DiskusiInterface diskusiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(DiskusiInterface.class);
        Call<List<DiskusiModel>> call = diskusiInterface.getPost(groupId);
        call.enqueue(new Callback<List<DiskusiModel>>() {
            @Override
            public void onResponse(Call<List<DiskusiModel>> call, final Response<List<DiskusiModel>> response) {
                try {
                    final List<DiskusiModel> diskusiModels = response.body();

                    postId = new ArrayList<Integer>();

                    adapter = new DiskusiAdapter(diskusiModels, new OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, final int position, boolean isLongClick) {
                            if (isLongClick) {

                                kick.setText("Delete this post?");
                                kick.setVisibility(View.VISIBLE);
                                kick.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        deletePost(groupId, diskusiModels.get(position).getId());
                                        adapter.removeItem(position);
                                        kick.setVisibility(View.GONE);
                                    }
                                });
                            } else {
                                Intent intent = new Intent(getActivity(), DiskusiDetail.class);
                                intent.putExtra("postId", diskusiModels.get(position).getId());
                                intent.putExtra("groupId", groupId);
                                startActivity(intent);
                            }
                        }
                    });
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    stopAnim();
                    fail.setVisibility(View.GONE);
                } catch (Exception e) {
                    Log.e(TAG, "error", e);
                }
            }

            @Override
            public void onFailure(Call<List<DiskusiModel>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                stopAnim();
                fail.setVisibility(View.VISIBLE);
            }
        });
    }

    public void startAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        inflated.findViewById(R.id.load).setVisibility(View.GONE);
    }

    private void deletePost(int groupId, int postId) {
        DiskusiInterface service = new RetrofitBuilder(getActivity()).getRetrofit().create(DiskusiInterface.class);
        Call<ResponseBody> call = service.deletePost(groupId, postId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: del " + response.code());
                reqJson();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
