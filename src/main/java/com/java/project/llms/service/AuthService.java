package com.java.project.llms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java.project.llms.entity.UserDetailsEntity;
import com.java.project.llms.repository.UserDetailsRepository;
import com.java.project.llms.utils.JwtUtil;
import com.java.project.llms.vos.ResponseVo;
import com.java.project.llms.vos.UserRequestVo;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private UserDetailsRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public ResponseVo register(UserRequestVo request) {
		logger.error("Attempting to register user with email: {}", request.getEmail());

		try {

			if (request.getEmail() == null || request.getEmail().isEmpty()) {
				logger.error("Registration failed: Email is empty");
				return new ResponseVo(null, "Email cannot be empty", "");
			}
			if (request.getPassword() == null || request.getPassword().isEmpty()) {
				logger.error("Registration failed: Password is empty");
				return new ResponseVo(null, "Password cannot be empty", "");
			}

			UserDetailsEntity existingUser = userRepository.findByEmail(request.getEmail());
			if (existingUser != null) {
				logger.error("User with email {} already registered", request.getEmail());
				return new ResponseVo(null, "User already registered. Please login", "");
			}

			UserDetailsEntity user =userRepository.save(createUser(request));
			logger.error("User registered successfully with email: {}", request.getEmail());
			return new ResponseVo("User registration successful", null, user.getApiToken());

		} catch (Exception e) {
			logger.error("Error during user registration for email: {}", request.getEmail(), e);
			return new ResponseVo(null, "Registration failed due to an internal error", "");
		}
	}
	
	private UserDetailsEntity createUser(UserRequestVo request) {
        UserDetailsEntity user = new UserDetailsEntity();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setApiToken(generateApiToken());
        return user;
    }

	public ResponseVo login(UserRequestVo request) {
		logger.error("Login attempt for email: {}", request.getEmail());

		try {

			if (request.getEmail() == null || request.getEmail().isEmpty()) {
				logger.error("Login failed: Email is empty");
				return new ResponseVo(null, "Email cannot be empty", "");
			}
			if (request.getPassword() == null || request.getPassword().isEmpty()) {
				logger.error("Login failed: Password is empty");
				return new ResponseVo(null, "Password cannot be empty", "");
			}

			UserDetailsEntity user = userRepository.findByEmail(request.getEmail());
			if (user == null) {
				logger.error("Login failed: User not found for email {}", request.getEmail());
				return new ResponseVo(null, "User not found", "");
			}

			if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				String token = jwtUtil.generateToken(request.getEmail());
				logger.error("Login successful for email: {}", request.getEmail());
				return new ResponseVo(token, null, "Login successful");
			} else {
				logger.error("Login failed: Invalid credentials for email {}", request.getEmail());
				return new ResponseVo(null, "Invalid credentials", "");
			}

		} catch (Exception e) {
			logger.error("Error during login for email: {}", request.getEmail(), e);
			return new ResponseVo(null, "Login failed due to an internal error", "");
		}
	}

	public ResponseVo updatePassword(UserRequestVo request) {
		logger.error("Password update attempt for email: {}", request.getEmail());

		try {

			if (request.getEmail() == null || request.getEmail().isEmpty()) {
				logger.error("Password update failed: Email is empty");
				return new ResponseVo(null, "Email cannot be empty", "");
			}
			if (request.getPassword() == null || request.getPassword().isEmpty()) {
				logger.error("Password update failed: Old password is empty");
				return new ResponseVo(null, "Old password cannot be empty", "");
			}
			if (request.getNewPassword() == null || request.getNewPassword().isEmpty()) {
				logger.error("Password update failed: New password is empty");
				return new ResponseVo(null, "New password cannot be empty", "");
			}

			UserDetailsEntity user = userRepository.findByEmail(request.getEmail());
			if (user == null) {
				logger.error("Password update failed: User not found for email {}", request.getEmail());
				return new ResponseVo(null, "User not found", "");
			}

			if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
				user.setPassword(passwordEncoder.encode(request.getNewPassword()));
				userRepository.save(user);
				logger.error("Password updated successfully for email: {}", request.getEmail());
				return new ResponseVo("Password updated successfully", null, "");
			} else {
				logger.error("Password update failed: Invalid old password for email {}", request.getEmail());
				return new ResponseVo(null, "Invalid old password", "");
			}

		} catch (Exception e) {
			logger.error("Error during password update for email: {}", request.getEmail(), e);
			return new ResponseVo(null, "Password update failed due to an internal error", "");
		}
	}

	private String generateApiToken() {
		try {
			byte[] randomBytes = new byte[16];
			new SecureRandom().nextBytes(randomBytes);
			String token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
			logger.debug("Generated API token: {}", token);
			return token;
		} catch (Exception e) {
			logger.error("Error generating API token", e);
			throw new RuntimeException("Failed to generate API token", e);
		}
	}
}