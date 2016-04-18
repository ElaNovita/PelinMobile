package com.example.ela.pelinmobile.Fragment;



import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.AllGroups;
import com.example.ela.pelinmobile.GroupDetail;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.HomeDosen;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;
import com.example.ela.pelinmobile.OnItemClickListener;
import com.example.ela.pelinmobile.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupListFragment extends Fragment {
    RecyclerView groupRv;
    private FragmentActivity myContenxt;
    @Bind(R.id.tablayout)
    TabLayout tabLayout;
    RecyclerView recyclerView;
    boolean undoOn;
    List<String> items;
    List<String> itemsPendingRemoval;

    private List<GroupModel> groups;

    public GroupListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroupInterface groupInterface = new RetrofitBuilder(getActivity()).getRetrofit().create(GroupInterface.class);
        Call<List<GroupModel>> call = groupInterface.getGroups();
        call.enqueue(new Callback<List<GroupModel>>() {
            @Override
            public void onResponse(Call<List<GroupModel>> call, Response<List<GroupModel>> response) {
                try {
                    List<GroupModel> groups = response.body();

                    GroupListAdapter adapter = new GroupListAdapter(groups, new OnItemClickListener() {
                        @Override
                        public void onItemClick(GroupModel group) {
                            Toast.makeText(getActivity(), "tes", Toast.LENGTH_SHORT).show();
                        }
                    });

                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Log.e("respon", "onResponse: error", e);
                }
            }

            @Override
            public void onFailure(Call<List<GroupModel>> call, Throwable t) {

            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflated = inflater.inflate(R.layout.fragment_group_list, container, false);
        recyclerView = (RecyclerView) inflated.findViewById(R.id.groupRv);
        FloatingActionButton fab = (FloatingActionButton) inflated.findViewById(R.id.addGroup);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
//        GroupListAdapter adapter = new GroupListAdapter(groups, new OnItemClickListener() {
//            @Override
//            public void onItemClick(Group group) {
//                Intent intent = new Intent(getActivity(), GroupDetail.class);
//                startActivity(intent);
//            }
//        });
//        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
//        addGroupFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), GroupDetail.class);
//                startActivity(intent);
//            }
//        });



        return inflated;


    }


    public void showDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        CreateGroupDialog createGroupDialog = CreateGroupDialog.newInstance("Create New Group");
        createGroupDialog.show(fragmentManager, "title");
    }


}









