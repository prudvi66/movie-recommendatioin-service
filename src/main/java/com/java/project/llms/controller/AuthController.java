package com.java.project.llms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.java.project.llms.service.AuthService;
import com.java.project.llms.vos.ResponseVo;
import com.java.project.llms.vos.UserRequestVo;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin()
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseVo register(@RequestBody UserRequestVo request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseVo login(@RequestBody UserRequestVo request) {
        return authService.login(request);
    }

	@PutMapping("/update-password")
	public ResponseVo updatePassword(@RequestBody UserRequestVo request) {
		return authService.updatePassword(request);
	}

    
}