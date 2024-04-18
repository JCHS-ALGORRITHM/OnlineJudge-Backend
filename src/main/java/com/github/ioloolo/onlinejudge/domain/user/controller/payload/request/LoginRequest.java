package com.github.ioloolo.onlinejudge.domain.user.controller.payload.request;

import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {

	@NotBlank(groups = NotBlankGroup.class, message = "아이디는 필수 입력값입니다.")
	private String username;

	@NotBlank(groups = NotBlankGroup.class, message = "비밀번호는 필수 입력값입니다.")
	private String password;
}
