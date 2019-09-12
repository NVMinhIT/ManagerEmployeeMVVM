package com.example.manageremployee_mvvm.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.manageremployee_mvvm.R;
import com.example.manageremployee_mvvm.databinding.ActivityMainBinding;
import com.example.manageremployee_mvvm.model.Employee;
import com.example.manageremployee_mvvm.view.adapter.MainAdapter;
import com.example.manageremployee_mvvm.view.addemployee.AddEmployeeActivity;
import com.example.manageremployee_mvvm.view.detail.DetailEmployeeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IOnClickListeners<Employee>, View.OnClickListener {
    FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private List<Employee> employeeList;
    private EditText edtSearch;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainViewModel mainViewModel;
    private Boolean isFirst = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setLifecycleOwner(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainBinding.setModel(mainViewModel);
        mainViewModel.showListEmploy();
        initView();

    }
    private void initView() {
        mainViewModel.getUser().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                mainAdapter.setEmployeelList(employees);
                mainAdapter.notifyDataSetChanged();
            }
        });
        swipeRefreshLayout = findViewById(R.id.content_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //showListEmploy();
                mainViewModel.showListEmploy();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView = findViewById(R.id.rv_list_employer);
        employeeList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        mainAdapter = new MainAdapter(this, employeeList);
        recyclerView.setAdapter(mainAdapter);
        edtSearch = findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edtSearch.getText().toString().equals("")) {
                    mainViewModel.showListEmploy();
                    mainAdapter.setListEmployer(employeeList);
                } else {
                    search(edtSearch.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void search(String name) {
        ArrayList<Employee> arr = new ArrayList<>();
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmployeeName().contains(name)) {
                arr.add(employeeList.get(i));
            }
        }
        mainAdapter.setListEmployer(arr);
    }

    @Override
    public void onClick(Employee employee) {
        Intent intent = new Intent(MainActivity.this, DetailEmployeeActivity.class);
        intent.putExtra("ID", employee.getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddEmployeeActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isFirst) {
            mainViewModel.showListEmploy();
        } else {
            isFirst = false;
        }
    }
}
