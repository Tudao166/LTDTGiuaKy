package com.example.testgiuaky2.service;

import com.example.testgiuaky2.config.RetrofitCilent;
import com.example.testgiuaky2.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private final ApiService apiService;

    public UserService() {
        apiService = RetrofitCilent.getRetrofitInstance().create(ApiService.class);
    }

    public void fetchUserData(int userId, UserCallback callback) {
        Call<User> call = apiService.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                }
                else {
                    callback.onFailure("Response failed!!!" + response);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onFailure("Error when call API: " + t.getMessage());
            }
        });
    }

    public interface UserCallback {
        void onSuccess(User user);
        void onFailure(String errorMessage);
    }
}
