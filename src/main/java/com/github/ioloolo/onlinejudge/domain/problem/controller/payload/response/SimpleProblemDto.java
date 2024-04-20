package com.github.ioloolo.onlinejudge.domain.problem.controller.payload.response;

import java.util.List;

import com.github.ioloolo.onlinejudge.domain.problem.entity.Problem;
import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleProblemDto {

	private int id;
	private String name;
	private List<Tag> tag;

	public static SimpleProblemDto from(Problem problem) {
		return SimpleProblemDto.builder()
				.id(problem.getProblemId())
				.name(problem.getName())
				.tag(problem.getTags())
				.build();
	}
}
