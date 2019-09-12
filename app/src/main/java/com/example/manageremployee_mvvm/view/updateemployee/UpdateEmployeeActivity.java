package com.example.manageremployee_mvvm.view.updateemployee;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.manageremployee_mvvm.R;
import com.example.manageremployee_mvvm.api.response.EmployeeBodyResponse;
import com.example.manageremployee_mvvm.databinding.ActivityUpdateEmployeeBinding;
import com.example.manageremployee_mvvm.repository.ApiService;
import com.example.manageremployee_mvvm.repository.ServiceEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    private String idUpDate;
    private UpdateEmployViewModel updateEmployViewModel;
    private Button imageUpdate;
    private String Idmoi;
    private EditText name, salary, age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUpdateEmployeeBinding updateEmployeeBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_employee);
        updateEmployViewModel = ViewModelProviders.of(this).get(UpdateEmployViewModel.class);
        updateEmployeeBinding.setLifecycleOwner(this);
        updateEmployeeBinding.setViewModel(updateEmployViewModel);
        Intent intent = getIntent();
        idUpDate = intent.getStringExtra("BUNDLE_ID");
        updateEmployViewModel.getEmployUpdate(idUpDate);
        updateEmployViewModel.getIdCurrent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Idmoi = s;
            }
        });
        initView();
    }

    private void initView() {
        name = findViewById(R.id.edt_update_name);
        salary = findViewById(R.id.edt_update_salary);
        age = findViewById(R.id.edt_age_update);
        imageUpdate = findViewById(R.id.btn_save_update);
        imageUpdate.setOnClickListener(this);
    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClick(View view) {
        ApiService service = ServiceEmployee.createService(ApiService.class);
        final Call<EmployeeBodyResponse> call = service.getEmployUpdate(Idmoi, new EmployeeBodyResponse(name.getText().toString(), salary.getText().toString(), age.getText().toString()));
        ProgressDialog progressDialog = new ProgressDialog(UpdateEmployeeActivity.this);
        progressDialog.setMessage("Updating");
        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                call.cancel();
            }
        });
        progressDialog.show();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                call.enqueue(new Callback<EmployeeBodyResponse>() {
                    @Override
                    public void onResponse(Call<EmployeeBodyResponse> call, Response<EmployeeBodyResponse> response) {
                        EmployeeBodyResponse bodyResponse = response.body();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<EmployeeBodyResponse> call, Throwable t) {
                        Toast.makeText(UpdateEmployeeActivity.this, "API Not Responding...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }.execute();

    }
}
