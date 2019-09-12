package com.example.manageremployee_mvvm.view.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.manageremployee_mvvm.repository.ApiService;
import com.example.manageremployee_mvvm.repository.ServiceEmployee;
import com.example.manageremployee_mvvm.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<Employee>> mutableLiveData;

    public LiveData<List<Employee>> getUser() {

        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;

    }

    void showListEmploy() {
        ApiService service = ServiceEmployee.createService(ApiService.class);
        service.getResultEmployer().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                List<Employee> list = response.body();
                mutableLiveData.postValue(list);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                //Toast.makeText(get, "Not response", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
