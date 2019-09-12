package com.example.manageremployee_mvvm.repository;

import com.example.manageremployee_mvvm.api.response.CreateEmployeeResponse;
import com.example.manageremployee_mvvm.api.response.DeleteEmployResponse;
import com.example.manageremployee_mvvm.api.response.EmployeeBodyResponse;
import com.example.manageremployee_mvvm.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @Headers("Content-Type: application/json ")
    @GET("employees")
    Call<List<Employee>> getResultEmployer();

    @Headers("Content-Type: application/json ")
    @DELETE("delete/{id}")
    Call<DeleteEmployResponse> getDeleteEmploy(@Path("id") String Id);

    @Headers("Content-Type: application/json ")
    @GET("employee/{id}")
    Call<Employee> getEmployee(@Path("id") String id);

    @Headers("Content-Type: application/json ")
    @POST("create")
    Call<CreateEmployeeResponse> getAllEmployeeCreate(@Body EmployeeBodyResponse employBody);

    @Headers("Content-Type: application/json ")
    @PUT("update/{id}")
    Call<EmployeeBodyResponse> getEmployUpdate(@Path("id") String id, @Body EmployeeBodyResponse employBody);
}
