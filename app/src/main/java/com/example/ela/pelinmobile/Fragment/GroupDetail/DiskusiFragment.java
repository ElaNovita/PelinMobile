package com.example.ela.pelinmobile.Fragment.GroupDetail;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.DiskusiAdapter;
import com.example.ela.pelinmobile.Fragment.CreatePost;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.DiskusiInterface;
import com.example.ela.pelinmobile.Model.DiskusiModel;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

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

    public DiskusiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getArguments().getInt("groupId");

        DiskusiInterface diskusiInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(DiskusiInterface.class);
        Call<List<DiskusiModel>> call = diskusiInterface.getPost(groupId);
        call.enqueue(new Callback<List<DiskusiModel>>() {
            @Override
            public void onResponse(Call<List<DiskusiModel>> call, final Response<List<DiskusiModel>> response) {
                try {
                    final List<DiskusiModel> diskusiModels = response.body();

                    postId = new ArrayList<Integer>();

                    DiskusiAdapter adapter = new DiskusiAdapter(diskusiModels, new DiskusiAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(DiskusiModel diskusi, int position) {

                            Intent intent = new Intent(getActivity(), DiskusiDetail.class);
                            intent.putExtra("postId", diskusi.getId());
                            intent.putExtra("groupId", groupId);
                            startActivity(intent);

                        }
                    });
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e(TAG, "error", e);
                }
            }

            @Override
            public void onFailure(Call<List<DiskusiModel>> call, Throwable t) {

            }
        });

        bundle = new Bundle();

        bundle.putInt("groupId", groupId);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_diskusi, container, false);

        recyclerView = (RecyclerView) inflated.findViewById(R.id.dRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.diskusi);

        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addDiskusi);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
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

}
