package com.jojo.gezginservice.service;

import com.jojo.gezginservice.exceptions.GeneralException;
import com.jojo.gezginservice.exceptions.Message;
import com.jojo.gezginservice.model.User;
import com.jojo.gezginservice.model.enums.ErrorCode;
import com.jojo.gezginservice.request.LoginRequest;
import com.jojo.gezginservice.request.UserRequest;
import com.jojo.gezginservice.response.TokenResponse;
import com.jojo.gezginservice.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder encoder;


    public TokenResponse login(LoginRequest loginRequest) {
        try {
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));

            return TokenResponse
                    .builder()
                    .accessToken(tokenService.generateToken(auth))
                    .user(userService.getUserDto(loginRequest.getEmail()))
                    .build();

        } catch (final BadCredentialsException badCredentialsException) {
            throw new GeneralException(Message.INVALID, HttpStatus.BAD_REQUEST, ErrorCode.INVALID);
        }
    }

    public UserResponse getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserDto(username);
    }

    @Transactional
    public UserResponse register(UserRequest userRequest) throws Exception {
        var isAllReadyRegistered = userService.existsByEmail(userRequest.getEmail());

        if (isAllReadyRegistered) {
            new GeneralException(Message.ALREADY_USED_EMAIL, HttpStatus.FOUND, ErrorCode.FOUND);
        }

        var user = User.builder()
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .userType(userRequest.getUserType())
                .phoneNumber(userRequest.getPhoneNumber())
                .build();

        User fromDb = userService.createUser(user);

        return UserResponse.builder()
                .id(fromDb.getId())
                .email(fromDb.getEmail())
                .role(fromDb.getRole())
                .phoneNumber(fromDb.getPhoneNumber())
                .userType(fromDb.getUserType())
                .build();

    }
}
