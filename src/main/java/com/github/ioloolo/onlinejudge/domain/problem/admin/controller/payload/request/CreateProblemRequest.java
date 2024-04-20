package com.github.ioloolo.onlinejudge.domain.problem.admin.controller.payload.request;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.github.ioloolo.onlinejudge.common.validation.group.LengthGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.MinMaxGroup;
import com.github.ioloolo.onlinejudge.common.validation.group.NotBlankGroup;
import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProblemRequest {

	@NotBlank(groups = NotBlankGroup.class, message = "문제 ID는 필수 입력값입니다.")
	@Min(groups = MinMaxGroup.class, value = 1000, message = "문제 ID는 1000번 이상이어야 합니다.")
	@Max(groups = MinMaxGroup.class, value = 99999, message = "문제 ID는 99999번 이하이어야 합니다.")
	private int problemId;

	@NotBlank(groups = NotBlankGroup.class, message = "문제 이름은 필수 입력값입니다.")
	@Length(groups = LengthGroup.class, min = 2, max = 50, message = "문제 이름은 2자 이상, 50자 이하로 입력해주세요.")
	private String name;

	@NotBlank(groups = NotBlankGroup.class, message = "문제 설명은 필수 입력값입니다.")
	@Length(groups = LengthGroup.class, min = 2, max = 1000, message = "문제 설명은 2자 이상, 1000자 이하로 입력해주세요.")
	private String description;

	@NotBlank(groups = NotBlankGroup.class, message = "입력 설명은 필수 입력값입니다.")
	@Length(groups = LengthGroup.class, min = 2, max = 1000, message = "입력 설명은 2자 이상, 1000자 이하로 입력해주세요.")
	private String inputDescription;

	@NotBlank(groups = NotBlankGroup.class, message = "출력 설명은 필수 입력값입니다.")
	@Length(groups = LengthGroup.class, min = 2, max = 1000, message = "출력 설명은 2자 이상, 1000자 이하로 입력해주세요.")
	private String outputDescription;

	@Min(groups = MinMaxGroup.class, value = 100, message = "제한 시간은 100ms 이상이어야 합니다.")
	@Max(groups = MinMaxGroup.class, value = 5000, message = "제한 시간은 5000ms 이하이어야 합니다.")
	long timeLimit;

	@Min(groups = MinMaxGroup.class, value = 1, message = "제한 메모리는 1MB 이상이어야 합니다.")
	@Max(groups = MinMaxGroup.class, value = 256, message = "제한 메모리는 256MB 이하이어야 합니다.")
	long memoryLimit;

	@DBRef
	@Singular
	@Length(groups = LengthGroup.class, min = 1, max = 10, message = "태그는 1개 이상, 10개 이하로 입력해주세요.")
	List<Tag> tags;
}
