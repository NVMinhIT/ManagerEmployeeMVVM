package com.example.manageremployee_mvvm.view.addemployee;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.manageremployee_mvvm.R;
import com.example.manageremployee_mvvm.api.response.CreateEmployeeResponse;
import com.example.manageremployee_mvvm.api.response.EmployeeBodyResponse;
import com.example.manageremployee_mvvm.databinding.ActivityAddEmployeeBinding;

import java.util.Objects;

public class AddEmployeeActivity extends AppCompatActivity {
    private ActivityAddEmployeeBinding activityAddEmployeeBinding;
    private AddEmployeeViewModel addEmployeeViewModel;
    private Button btCancel;
    private TextView tName, tSalary, tAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddEmployeeBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_employee);
        activityAddEmployeeBinding.setLifecycleOwner(this);
        addEmployeeViewModel = ViewModelProviders.of(this).get(AddEmployeeViewModel.class);
        activityAddEmployeeBinding.setModel(addEmployeeViewModel);
        initView();
        addEmployeeViewModel.getLiveData().observe(this, new Observer<EmployeeBodyResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChanged(EmployeeBodyResponse employeeBodyResponse) {
                if (TextUtils.isEmpty(Objects.requireNonNull(employeeBodyResponse).getName())) {
                    activityAddEmployeeBinding.edtName.setError("Enter a Name");
                    activityAddEmployeeBinding.edtName.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(employeeBodyResponse).getSalary())) {
                    activityAddEmployeeBinding.edtSalary.setError("Enter a Saraly");
                    activityAddEmployeeBinding.edtSalary.requestFocus();
                } else if (TextUtils.isEmpty(Objects.requireNonNull(employeeBodyResponse).getAge())) {
                    activityAddEmployeeBinding.edtAge.setError("Enter a Age");
                    activityAddEmployeeBinding.edtAge.requestFocus();
                }
            }
        });
        addEmployeeViewModel.getCreateEmployeeResponse().observe(this, new Observer<CreateEmployeeResponse>() {
            @Override
            public void onChanged(CreateEmployeeResponse createEmployeeResponse) {
                finish();

            }
        });

    }

    private void initView() {
        btCancel = findViewById(R.id.btn_cancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
