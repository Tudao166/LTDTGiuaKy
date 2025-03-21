package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.model.*;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    AuthService authService;
    UserService userService;
    IUserRepository userRepository;

    @GetMapping("/demo")
    ApiResponse<String> demo() {
        UserEntity user = UserEntity.builder()
                .email("tudao")
                .password("1234")
                .picture("test")
                .fullName("test")
                .build();
        userRepository.save(
                user
        );
        return ApiResponse.<String>builder()
                .message("")
                .code(200)
                .result(user.getEmail())
                .build();
    }

    @PostMapping("/register")
    ApiResponse<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        UserResponse user = userService.register(registerRequest);
        String message;
        if(user == null){
            message="Email đã tồn tại";
            return ApiResponse.<UserResponse>builder()
                    .code(200)
                    .result(null)
                    .message(message)
                    .build();
        }else {
            message ="Đăng kí thành công";
            return ApiResponse.<UserResponse>builder()
                    .code(200)
                    .message(message)
                    .result(user)
                    .build();
        }
    }

    @PostMapping("/login")
    ApiResponse<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        return ApiResponse.<UserResponse>builder()
                .message("")
                .code(200)
                .message("")
                .result(userService.login(loginRequest))
                .build();
    }


    @PostMapping("/verify-user")
    ApiResponse<String> verifyUser(@RequestBody VerifyRequest request) {
        int status = authService.verifyUser(request.getEmail(), request.getOtp());

        if (status == -1) {
            return ApiResponse.<String>builder()
                    .code(404)
                    .message("Email not found")
                    .build();
        } else if (status == 0) {
            return ApiResponse.<String>builder()
                    .code(400)
                    .message("OTP does not match")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(200)
                    .message("Verification successful")
                    .build();
        }
    }
}
