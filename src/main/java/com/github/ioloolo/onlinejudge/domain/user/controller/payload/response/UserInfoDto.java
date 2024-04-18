package com.github.ioloolo.onlinejudge.domain.user.controller.payload.response;

import lombok.Data;

@Data(staticConstructor = "of")
public class UserInfoDto {

	private final String username;
	private final String realName;
	private final int schoolGrade;
	private final int schoolClass;
	private final int schoolId;
	private final boolean admin;
}
