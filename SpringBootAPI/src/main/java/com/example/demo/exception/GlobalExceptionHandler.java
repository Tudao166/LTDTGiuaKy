package com.example.demo.exception;


import com.example.demo.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Lỗi này dùng khi không có một message nào xảy ra khi có lỗi
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> exceptionHandler(Exception e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> appExceptionHandler(AppException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(e.getErrorCode().getCode());
        apiResponse.setMessage(e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatusCode())   // Set HttpStatusCode
                .body(apiResponse);
    }

    // Exception xử lý quyền không đủ quyền để truy cập vào một endpoint (AccessDenied)
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

}
