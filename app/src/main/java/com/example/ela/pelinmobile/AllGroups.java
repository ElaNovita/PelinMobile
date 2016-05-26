package com.example.ela.pelinmobile;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.Helper.RetrofitBuilder;
import com.example.ela.pelinmobile.Interface.GroupInterface;
import com.example.ela.pelinmobile.Model.Group;
import com.example.ela.pelinmobile.Model.GroupModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e on 7/04/16.
 */
public class AllGroups extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private MenuItem mSearchAction;
    private boolean isSearchOpned = false;
    private EditText seacrhText;
    private List<GroupModel> groupModels;
    private AllGroupAdapter mAdapter;
    private RecyclerView recyclerView;
    boolean isJoined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_groups);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.allGroupRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        startAnim();
        reqJson();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searh_view, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<GroupModel> filteredGroup = filter(groupModels, query);
        mAdapter.animateTo(filteredGroup);
        recyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    private List<GroupModel> filter(List<GroupModel> groups, String query) {
        query = query.toLowerCase();

        final List<GroupModel> filteredGroup = new ArrayList<>();
        for (GroupModel group : groups) {
            final String text = group.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredGroup.add(group);
            }
        }

        return filteredGroup;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void reqJson() {
        Log.d("reqjson", "reqJson: " );

        GroupInterface service = new RetrofitBuilder(getApplicationContext()).getRetrofit().create(GroupInterface.class);
        Call<List<GroupModel>> call = service.getGroups();
        call.enqueue(new Callback<List<GroupModel>>() {
            @Override
            public void onResponse(Call<List<GroupModel>> call, final Response<List<GroupModel>> response) {
                Log.d("respon", "onResponse: hasil " + response.code());
                groupModels = response.body();

                mAdapter = new AllGroupAdapter(groupModels, getApplicationContext(), new AllGroupAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(GroupModel group) {
                        Log.d("respon", "OnItemClick: res " + response.code());
                    }
                });

                recyclerView.setAdapter(mAdapter);
                stopAnim();
            }

            @Override
            public void onFailure(Call<List<GroupModel>> call, Throwable t) {
                Log.e("respon", "onFailure: gagal ", t);
                stopAnim();
            }
        });
    }

    public void startAnim() {
        findViewById(R.id.load).setVisibility(View.VISIBLE);
    }

    public void stopAnim() {
        findViewById(R.id.load).setVisibility(View.GONE);
    }
}