package com.github.ioloolo.onlinejudge.domain.lecture.admin.controller.payload.request;

import com.github.ioloolo.onlinejudge.common.validation.group.LengthGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeTagRequest {

	@NotBlank(groups = NotBlankGroup.class, message = "기존 태그 ID는 필수 입력값입니다.")
	private String tagId;

	@NotBlank(groups = NotBlankGroup.class, message = "새 태그는 필수 입력값입니다.")
	@Size(groups = LengthGroup.class, min = 2, max = 12, message = "태그는 2자 이상, 12자 이하로 입력해주세요.")
	private String newTag;
}
