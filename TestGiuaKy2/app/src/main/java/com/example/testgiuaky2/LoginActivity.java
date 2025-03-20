package com.example.testgiuaky2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testgiuaky2.config.PrefManager;
import com.example.testgiuaky2.config.RetrofitCilent;
import com.example.testgiuaky2.model.Account;
import com.example.testgiuaky2.model.AccountResponse;
import com.example.testgiuaky2.model.ApiResponse;
import com.example.testgiuaky2.model.User;
import com.example.testgiuaky2.service.ApiService;
import com.example.testgiuaky2.service.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private ImageView btnLogin;
    private EditText edtEmail, edtPass;
    private ApiService apiService;
    //    private LoginService loginService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TextView tvRegister = findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        //loginService = new LoginService();
        apiService = RetrofitCilent.getRetrofitInstance().create(ApiService.class);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPass.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập email và mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("MainActivity", "Gửi yêu cầu đăng nhập...");

                Account request = new Account(email, password);
                apiService.login(request).enqueue(new Callback<ApiResponse<User>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<User>> call, Response<ApiResponse<User>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", "Đăng nhập thành công!");
                            User user = response.body().getResult();

                            PrefManager prefManager = new PrefManager(LoginActivity.this);
                            prefManager.saveLoginDetails(user.getFullName(), user.getEmail(), user.getPicture());

                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Log.d("LoginActivity", "Đăng nhập thành công!");

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            int errorCode = response.code();
                            String errorMessage = "Đăng nhập thất bại! Mã lỗi: " + errorCode;
                            String errorBody = response.errorBody() != null ? response.errorBody().toString() : "Không có thông tin lỗi";
                            errorMessage += "\nChi tiết: " + errorBody;

                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            Log.d("MainActivity", errorMessage);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("MainActivity", "Lỗi kết nối: " + t.getMessage());
                    }
                });
            }
        });
    }
}
