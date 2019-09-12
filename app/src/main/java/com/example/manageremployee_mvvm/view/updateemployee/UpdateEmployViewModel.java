package com.example.manageremployee_mvvm.view.updateemployee;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manageremployee_mvvm.model.Employee;
import com.example.manageremployee_mvvm.repository.ApiService;
import com.example.manageremployee_mvvm.repository.ServiceEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateEmployViewModel extends ViewModel {
    public MutableLiveData<String> idCurrent = new MutableLiveData<>();
    public MutableLiveData<String> updateName = new MutableLiveData<>();
    public MutableLiveData<String> updateSalary = new MutableLiveData<>();
    public MutableLiveData<String> updateAge = new MutableLiveData<>();

    public MutableLiveData<String> getIdCurrent() {
        return idCurrent;
    }

    public MutableLiveData<String> getUpdateName() {
        return updateName;
    }

    public MutableLiveData<String> getUpdateSalary() {
        return updateSalary;
    }

    public MutableLiveData<String> getUpdateAge() {
        return updateAge;
    }

    void getEmployUpdate(String id) {
        ApiService service = ServiceEmployee.createService(ApiService.class);
        service.getEmployee(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                Employee employee = response.body();
                idCurrent.postValue(employee.getId());
                updateName.postValue(employee.getEmployeeName());
                updateSalary.postValue(employee.getEmploySalary());
                updateAge.postValue(employee.getEmployAge());
            }
            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

            }
        });

    }

}
