package com.github.ioloolo.onlinejudge.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.onlinejudge.common.payload.response.Response;
import com.github.ioloolo.onlinejudge.common.payload.response.SuccessResponse;
import com.github.ioloolo.onlinejudge.common.validation.OrderChecks;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.request.ChangeInfoRequest;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.request.ChangePasswordRequest;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.request.GetUserInfoRequest;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.request.LoginRequest;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.request.RegisterRequest;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.response.UserInfoDto;
import com.github.ioloolo.onlinejudge.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService service;

	@Operation(summary = "내 데이터 가져오기")
	@GetMapping
	public ResponseEntity<Response<UserInfoDto>> getSelfUser() {

		UserInfoDto userInfoDto = service.getSelfUser();

		return ResponseEntity.ok(Response.of(userInfoDto));
	}

	@Operation(summary = "유저 데이터 가져오기")
	@PostMapping
	public ResponseEntity<Response<UserInfoDto>> getUser(@Validated(OrderChecks.class) @RequestBody GetUserInfoRequest request) throws Exception {

		String username = request.getUsername();

		UserInfoDto userInfoDto = service.getUser(username);

		return ResponseEntity.ok(Response.of(userInfoDto));
	}

	@Operation(summary = "회원가입")
	@PutMapping("/auth")
	public ResponseEntity<SuccessResponse> register(@Validated(OrderChecks.class) @RequestBody RegisterRequest request) throws Exception {

		String username = request.getUsername();
		String password = request.getPassword();
		String realName = request.getRealName();
		int schoolGrade = request.getSchoolGrade();
		int schoolClass = request.getSchoolClass();
		int schoolId = request.getSchoolId();

		service.register(username, password, realName, schoolGrade, schoolClass, schoolId);

		return ResponseEntity.ok(new SuccessResponse());
	}

	@Operation(summary = "로그인")
	@PostMapping("/auth")
	public ResponseEntity<Response<String>> login(@Validated(OrderChecks.class) @RequestBody LoginRequest request) {

		String username = request.getUsername();
		String password = request.getPassword();

		String token = service.login(username, password);

		return ResponseEntity.ok(Response.of(token));
	}

	@Operation(summary = "비밀번호 변경")
	@PatchMapping("/password")
	public ResponseEntity<SuccessResponse> changePassword(@Validated(OrderChecks.class) @RequestBody ChangePasswordRequest request) throws Exception {

		String password = request.getPassword();
		String newPassword = request.getNewPassword();

		service.changePassword(password, newPassword);

		return ResponseEntity.ok(new SuccessResponse());
	}

	@Operation(summary = "유저 정보 변경")
	@PatchMapping("/info")
	public ResponseEntity<SuccessResponse> changeInfo(@Validated(OrderChecks.class) @RequestBody ChangeInfoRequest request) throws Exception {

		String realName = request.getRealName();
		int schoolGrade = request.getSchoolGrade();
		int schoolClass = request.getSchoolClass();
		int schoolId = request.getSchoolId();

		service.changeInfo(realName, schoolGrade, schoolClass, schoolId);

		return ResponseEntity.ok(new SuccessResponse());
	}
}
