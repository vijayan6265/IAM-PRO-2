package com.zaalima.iam_server.controller;

//package com.zaalima.iam.controller;

import com.zaalima.iam_server.dto.UserResponse;

import com.zaalima.iam_server.dto.RegisterRequest;
//import com.zaalima.iam_server.entity.User;
import com.zaalima.iam_server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
        @Valid @RequestBody RegisterRequest request) {

    UserResponse user = userService.registerUser(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(user);
}
}
