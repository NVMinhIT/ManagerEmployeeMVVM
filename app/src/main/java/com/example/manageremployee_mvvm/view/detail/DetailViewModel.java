package com.example.manageremployee_mvvm.view.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manageremployee_mvvm.model.Employee;
import com.example.manageremployee_mvvm.repository.ApiService;
import com.example.manageremployee_mvvm.repository.ServiceEmployee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    public MutableLiveData<String> idcurrent = new MutableLiveData<>();
    public MutableLiveData<String> fullname = new MutableLiveData<>();
    public MutableLiveData<String> salary = new MutableLiveData<>();
    public MutableLiveData<String> age = new MutableLiveData<>();

    public MutableLiveData<String> getIdcurrent() {
        return idcurrent;
    }

    public MutableLiveData<String> getFullname() {
        return fullname;
    }

    public MutableLiveData<String> getSalary() {
        return salary;
    }

    public MutableLiveData<String> getAge() {
        return age;
    }

    public void showEmployByID(String id) {
        ApiService service = ServiceEmployee.createService(ApiService.class);
        service.getEmployee(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                Employee employee = response.body();
                idcurrent.postValue(employee.getId());
                fullname.postValue(employee.getEmployeeName());
                salary.postValue(employee.getEmploySalary());
                age.postValue(employee.getEmployAge());

            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {

            }
        });

    }

    public void onClickUpdate() {

    }


}
