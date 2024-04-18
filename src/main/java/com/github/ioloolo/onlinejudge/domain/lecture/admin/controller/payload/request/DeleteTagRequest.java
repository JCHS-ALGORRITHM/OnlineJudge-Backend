package com.github.ioloolo.onlinejudge.domain.lecture.admin.controller.payload.request;

import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteTagRequest {

	@NotBlank(groups = NotBlankGroup.class, message = "기존 태그 ID는 필수 입력값입니다.")
	private String tagId;
}
