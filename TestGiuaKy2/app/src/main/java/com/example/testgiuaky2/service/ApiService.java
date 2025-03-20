package com.example.testgiuaky2.service;

import java.util.List;

import com.example.testgiuaky2.model.Account;
import com.example.testgiuaky2.model.ApiResponse;
import com.example.testgiuaky2.model.CategoryResponse;
import com.example.testgiuaky2.model.Account;
import com.example.testgiuaky2.model.ApiResponse;
import com.example.testgiuaky2.model.OtpRequest;
import com.example.testgiuaky2.model.Category;
import com.example.testgiuaky2.model.User;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/v1/users/{id}")
    Call<User> getUser(@Path("id") int userId);

    @GET("/v1/category/list")
    Call<ApiResponse<List<CategoryResponse>>> getCategory();

    @GET("api/v1/category/last-product")
    Call<ApiResponse<List<CategoryResponse>>> getCategoryByUser(@Query("username") String username);
    @GET("categories.php")
    Call<List<Category>> getCategoryAll();
    @POST("api/v1/login")
    Call<Account> login(@Path("username") String username, @Path("password") String password);

    @POST("api/v1/login")
    Call<ApiResponse<User>> login(@Body Account account);
    @POST("api/v1/verify-user")
    Call<ApiResponse> verifyOtp(@Body OtpRequest otpRequest);

    @POST("api/v1/register")
    Call<ApiResponse<User>> register(@Body User user);

}
