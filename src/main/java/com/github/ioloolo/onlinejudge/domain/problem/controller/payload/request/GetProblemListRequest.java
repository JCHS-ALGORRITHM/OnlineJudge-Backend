package com.github.ioloolo.onlinejudge.domain.problem.controller.payload.request;

import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetProblemListRequest {

	@Min(groups = NotBlankGroup.class, value = 1, message = "페이지는 1페이지 이상이어야 합니다.")
	private int page;
}
