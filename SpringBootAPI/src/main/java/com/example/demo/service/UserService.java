package com.example.demo.service;


import com.example.demo.entity.UserEntity;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.LoginRequest;
import com.example.demo.model.RegisterRequest;
import com.example.demo.model.UserResponse;
import com.example.demo.repository.IUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    IUserRepository userRepository;

    EmailService emailService;

    // Nguyễn Công Quý - 22110403
    public UserResponse login(LoginRequest request) {

        UserEntity user = userRepository.findByEmail(request.getUsername());
        if (user == null)
            throw new AppException(ErrorCode.USER_NOT_EXISTED);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

//        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword())
//                && user.getIsActive() == 1;
        boolean authenticated = true;
        if (!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);



        return UserResponse.builder()
                .isActive((user.getIsActive() == null || user.getIsActive() == 0) ? 0 : 1)
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .picture(user.getPicture())
                .build();
    }

    public UserResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()) != null) {
            log.info("Email existed");
            return null;
        }

        // Build new user for this request
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        UserEntity userEntity = UserEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(0)
                .picture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1rHMHPZtUE9En7ObJdHD_jaubF4tIQdRGdg&s")    //
                .build();

        try {
            // Tạo
            String otpCode = generateOtp();
            userEntity.setOtp(otpCode);
            var newUser = userRepository.save(userEntity);
            String subject = "🔑 Activate Your Account at MyApp!";
            String body = "Hello " + newUser.getFullName() + ",\n\n"
                    + "Thank you for signing up at MyApp. To activate your account, please use the following OTP code:\n\n"
                    + "🔒 Your OTP Code: " + otpCode + "\n\n"
                    + "This code is valid for the next 10 minutes. Please do not share this code with anyone.\n\n"
                    + "If you did not request this, please ignore this email.\n\n"
                    + "Best regards,\n";

            // Gửi
            emailService.sendSimpleMail(userEntity.getEmail(), subject, body);
            // Sau khi gửi thì trả về response
            // Nguyễn Công Quý - 22110403
            UserResponse userResponse = UserResponse.builder()
                    .fullName(newUser.getFullName())
                    .email(newUser.getEmail())
                    .password(newUser.getPassword())
                    .isActive((newUser.getIsActive() == null || newUser.getIsActive() == 0) ? 0 : 1)
                    .picture(newUser.getPicture())
                    .build();


            return userResponse;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORIZED);
        }
    }

    Random RANDOM = new Random();

    String generateOtp() {
        return String.format("%06d", RANDOM.nextInt(999999));
    }

    public String logout() {
        return "Logout successful";
    }

}
