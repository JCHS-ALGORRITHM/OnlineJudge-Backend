package com.github.ioloolo.onlinejudge.domain.problem.admin.controller.payload.request;

import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProblemRequest {

	@Min(groups = NotBlankGroup.class, value = 1, message = "문제 ID는 필수 입력값입니다.")
	private int problemId;
}
