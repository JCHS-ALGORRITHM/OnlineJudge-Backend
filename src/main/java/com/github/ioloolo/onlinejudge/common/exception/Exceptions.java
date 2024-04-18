package com.github.ioloolo.onlinejudge.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Exceptions {
	// User Controller
	USERNAME_EXISTS("해당 아이디로 가입된 계정이 존재합니다."),
	USERINFO_EXISTS("해당 학번으로 가입된 계정이 존재합니다."),
	USER_NOT_EXISTS("존재하지 않는 유저입니다."),
	PASSWORD_WRONG("잘못된 비밀번호입니다."),

	PROBLEM_NOT_FOUND("존재하지 않는 문제입니다."),

	JUDGE_IMAGE_NOT_FOUND("채점 컨테이너 이미지가 존재하지 않습니다."),
	;

	private final String message;

	public Exception exception() {
		return new Exception(message);
	}
}
