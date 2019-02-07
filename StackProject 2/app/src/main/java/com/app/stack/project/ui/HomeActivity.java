package com.app.stack.project.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.app.stack.project.R;
import com.app.stack.project.adapter.UserAdapter;
import com.app.stack.project.model.Item;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeView{

    private HomePresenterImpl presenter;
    private UserAdapter adapter;
    private List<Item> items = new ArrayList<>();
    private TextView noResultsTV;
    private RecyclerView usersRV;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new HomePresenterImpl(this);
        setUpViews();
    }

    private void setUpViews() {
        usersRV = findViewById(R.id.usersRV);
        noResultsTV = findViewById(R.id.noResultsTV);
        progressBar = findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        usersRV.setLayoutManager(layoutManager);
        adapter = new UserAdapter(items);
        usersRV.setAdapter(adapter);
        usersRV.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
        getUsers();
    }

    private void getUsers() {
        presenter.getUsers();
        noResultsTV.setVisibility(View.GONE);
        usersRV.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateUsers(List<Item> users) {
        progressBar.setVisibility(View.GONE);
        if(users != null && users.size() > 0){
            items.clear();
            items.addAll(users);
            adapter.notifyDataSetChanged();
            noResultsTV.setVisibility(View.GONE);
            usersRV.setVisibility(View.VISIBLE);
        }else{
            noResultsTV.setVisibility(View.VISIBLE);
            usersRV.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, "Some error occurred, please try again later...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
