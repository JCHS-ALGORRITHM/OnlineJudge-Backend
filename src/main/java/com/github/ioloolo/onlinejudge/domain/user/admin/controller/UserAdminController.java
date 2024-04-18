package com.github.ioloolo.onlinejudge.domain.user.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.onlinejudge.common.payload.response.Response;
import com.github.ioloolo.onlinejudge.common.validation.OrderChecks;
import com.github.ioloolo.onlinejudge.domain.user.admin.controller.payload.request.ChangeInfoRequest;
import com.github.ioloolo.onlinejudge.domain.user.admin.controller.payload.request.ChangePasswordRequest;
import com.github.ioloolo.onlinejudge.domain.user.admin.service.UserAdminService;
import com.github.ioloolo.onlinejudge.domain.user.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "[관리자] 유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/admin")
public class UserAdminController {

	private final UserAdminService service;

	@Operation(summary = "유저 목록 가져오기")
	@GetMapping("/users")
	public ResponseEntity<Response<List<User>>> getUserList() {

		List<User> userList = service.getUserList();

		return ResponseEntity.ok(Response.of(userList));
	}

	@Operation(summary = "다른 유저 비밀번호 변경")
	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(@Validated(OrderChecks.class) @RequestBody ChangePasswordRequest request) throws Exception {

		String username = request.getUsername();
		String newPassword = request.getNewPassword();

		service.changePassword(username, newPassword);

		return ResponseEntity.ok().build();
	}

	@Operation(summary = "다른 유저 정보 변경")
	@PatchMapping("/info")
	public ResponseEntity<Void> changeInfo(@Validated(OrderChecks.class) @RequestBody ChangeInfoRequest request) throws Exception {

		String username = request.getUsername();
		String realName = request.getRealName();
		int uGrade = request.getSchoolGrade();
		int uClass = request.getSchoolClass();
		int uId = request.getSchoolId();

		service.changeInfo(username, realName, uGrade, uClass, uId);

		return ResponseEntity.ok().build();
	}
}
