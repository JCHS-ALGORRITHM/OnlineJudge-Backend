package com.github.ioloolo.onlinejudge.domain.user.controller.payload.request;

import com.github.ioloolo.onlinejudge.common.validation.group.LengthGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.PatternGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {

	@NotBlank(groups = NotBlankGroup.class, message = "비밀번호는 필수 입력값입니다.")
	private String password;

	@NotBlank(groups = NotBlankGroup.class, message = "새 비밀번호는 필수 입력값입니다.")
	@Size(groups = LengthGroup.class, min = 8, max = 32, message = "새 비밀번호는 8자 이상, 24자 이하로 입력해주세요.")
	@Pattern(groups = PatternGroup.class, regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,32}$", message = "새 비밀번호는 영문 및 숫자가 사용되어야 합니다.")
	private String newPassword;
}
