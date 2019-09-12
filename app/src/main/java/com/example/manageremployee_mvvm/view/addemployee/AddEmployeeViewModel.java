package com.example.manageremployee_mvvm.view.addemployee;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manageremployee_mvvm.repository.ApiService;
import com.example.manageremployee_mvvm.api.response.CreateEmployeeResponse;
import com.example.manageremployee_mvvm.api.response.EmployeeBodyResponse;
import com.example.manageremployee_mvvm.repository.ServiceEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployeeViewModel extends ViewModel {
    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> salary = new MutableLiveData<>();
    public MutableLiveData<String> age = new MutableLiveData<>();
    private MutableLiveData<EmployeeBodyResponse> employeeBodyResponseLiveData;
    private MutableLiveData<CreateEmployeeResponse> CreateEmployeeResponse;

    public MutableLiveData<CreateEmployeeResponse> getCreateEmployeeResponse() {

        if (CreateEmployeeResponse == null) {
            CreateEmployeeResponse = new MutableLiveData<>();
        }
        return CreateEmployeeResponse;
    }
    public MutableLiveData<EmployeeBodyResponse> getLiveData() {

        if (employeeBodyResponseLiveData == null) {
            employeeBodyResponseLiveData = new MutableLiveData<>();
        }
        return employeeBodyResponseLiveData;

    }
    public void onClick(View view) {
        EmployeeBodyResponse employeeBodyResponse = new EmployeeBodyResponse(name.getValue(), salary.getValue(), age.getValue());
        employeeBodyResponseLiveData.setValue(employeeBodyResponse);
        createEmploy();
    }
    private void createEmploy() {
        ApiService service = ServiceEmployee.createService(ApiService.class);
        service.getAllEmployeeCreate(new EmployeeBodyResponse(name.getValue(), salary.getValue(), age.getValue())).enqueue(new Callback<CreateEmployeeResponse>() {
            @Override
            public void onResponse(Call<CreateEmployeeResponse> call, Response<CreateEmployeeResponse> response) {
                CreateEmployeeResponse responseEmployee = response.body();
                CreateEmployeeResponse.postValue(responseEmployee);
            }
            @Override
            public void onFailure(Call<CreateEmployeeResponse> call, Throwable t) {

            }


        });
    }


}
