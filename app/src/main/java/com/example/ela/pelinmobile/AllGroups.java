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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ela.pelinmobile.Adapter.GroupListAdapter;
import com.example.ela.pelinmobile.Model.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by e on 7/04/16.
 */
public class AllGroups extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private MenuItem mSearchAction;
    private boolean isSearchOpned = false;
    private EditText seacrhText;
    private List<Group> groups;
    private AllGroupAdapter mAdapter;
    private RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_groups);
        groups = Group.initData();
        recyclerView = (RecyclerView) findViewById(R.id.allGroupRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mAdapter = new AllGroupAdapter(groups, new AllGroupAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Group group) {
                Toast.makeText(getApplicationContext(), "You clicked this", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        inflater.inflate(R.menu.searh_view, menu);
        getMenuInflater().inflate(R.menu.searh_view, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Group> filteredGroup = filter(groups, query);
        mAdapter.animateTo(filteredGroup);
        recyclerView.scrollToPosition(0);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    private List<Group> filter(List<Group> groups, String query) {
        query = query.toLowerCase();

        final List<Group> filteredGroup = new ArrayList<>();
        for (Group group : groups) {
            final String text = group.getTitle().toLowerCase();
            if (text.contains(query)) {
                filteredGroup.add(group);
            }
        }

        return filteredGroup;
    }

}
