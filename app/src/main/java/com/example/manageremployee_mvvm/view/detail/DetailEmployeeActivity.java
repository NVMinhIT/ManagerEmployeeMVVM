package com.example.manageremployee_mvvm.view.detail;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.manageremployee_mvvm.R;
import com.example.manageremployee_mvvm.repository.ApiService;
import com.example.manageremployee_mvvm.api.response.DeleteEmployResponse;
import com.example.manageremployee_mvvm.repository.ServiceEmployee;
import com.example.manageremployee_mvvm.databinding.ActivityDetailEmployeeBinding;
import com.example.manageremployee_mvvm.view.updateemployee.UpdateEmployeeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEmployeeActivity extends AppCompatActivity implements View.OnClickListener {

    public String ide;
    public String idmoi;
    private DetailViewModel detailViewModel;
    private ImageButton imageButtonDelete;
    private ImageButton imageButtonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailEmployeeBinding activityDetailEmployeeBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail_employee);
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        Intent intent = getIntent();
        ide = intent.getStringExtra("ID");
        activityDetailEmployeeBinding.setLifecycleOwner(this);
        activityDetailEmployeeBinding.setViewModel(detailViewModel);
        detailViewModel.showEmployByID(ide);
        imageButtonDelete = findViewById(R.id.imb_delete);
        imageButtonUpdate = findViewById(R.id.imb_edit);
        imageButtonUpdate.setOnClickListener(this);
        imageButtonDelete.setOnClickListener(this);
        detailViewModel.getIdcurrent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                idmoi = s;
            }
        });

    }
    private void DialogDeleteUser() {
        final AlertDialog.Builder dialogxoa = new AlertDialog.Builder(this);
        dialogxoa.setMessage("Bạn có muốn xóa nhân viên này không?");
        dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiService service = ServiceEmployee.createService(ApiService.class);
                service.getDeleteEmploy(idmoi).enqueue(new Callback<DeleteEmployResponse>() {
                    @Override
                    public void onResponse(Call<DeleteEmployResponse> call, Response<DeleteEmployResponse> response) {
                        DeleteEmployResponse deleteEmployResponse = response.body();
                        Toast.makeText(DetailEmployeeActivity.this, "Đã xóa" + idmoi, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<DeleteEmployResponse> call, Throwable t) {

                    }
                });
            }
        });
        dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogxoa.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imb_delete:
                DialogDeleteUser();
                break;
            case R.id.imb_edit:
                Intent intent = new Intent(this, UpdateEmployeeActivity.class);
                intent.putExtra("BUNDLE_ID", idmoi);
                startActivity(intent);
                break;
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        detailViewModel.showEmployByID(ide);
    }
}
