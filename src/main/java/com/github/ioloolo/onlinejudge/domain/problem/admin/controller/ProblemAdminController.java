package com.github.ioloolo.onlinejudge.domain.problem.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.onlinejudge.common.payload.response.SuccessResponse;
import com.github.ioloolo.onlinejudge.common.validation.OrderChecks;
import com.github.ioloolo.onlinejudge.domain.problem.admin.controller.payload.request.ChangeProblemRequest;
import com.github.ioloolo.onlinejudge.domain.problem.admin.controller.payload.request.CreateProblemRequest;
import com.github.ioloolo.onlinejudge.domain.problem.admin.controller.payload.request.DeleteProblemRequest;
import com.github.ioloolo.onlinejudge.domain.problem.admin.service.ProblemAdminService;
import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@io.swagger.v3.oas.annotations.tags.Tag(name = "[관리자] 문제")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem/admin")
public class ProblemAdminController {

	private final ProblemAdminService service;

	@Operation(summary = "문제 만들기")
	@PutMapping
	public ResponseEntity<SuccessResponse> createProblem(
			@Validated(OrderChecks.class) @RequestBody CreateProblemRequest request
	) throws Exception {

		int problemId = request.getProblemId();
		String name = request.getName();
		String description = request.getDescription();
		String inputDescription = request.getInputDescription();
		String outputDescription = request.getOutputDescription();
		long timeLimit = request.getTimeLimit();
		long memoryLimit = request.getMemoryLimit();
		List<Tag> tags = request.getTags();

		service.createProblem(
				problemId,
				name,
				description,
				inputDescription,
				outputDescription,
				timeLimit,
				memoryLimit,
				tags
		);

		return ResponseEntity.ok(new SuccessResponse());
	}

	@Operation(summary = "문제 수정하기")
	@PatchMapping
	public ResponseEntity<SuccessResponse> changeProblem(
			@Validated(OrderChecks.class) @RequestBody ChangeProblemRequest request
	) throws Exception {

		int previousProblemId = request.getPreviousProblemId();
		int problemId = request.getProblemId();
		String name = request.getName();
		String description = request.getDescription();
		String inputDescription = request.getInputDescription();
		String outputDescription = request.getOutputDescription();
		long timeLimit = request.getTimeLimit();
		long memoryLimit = request.getMemoryLimit();
		List<Tag> tags = request.getTags();

		service.changeProblem(
				previousProblemId,
				problemId,
				name,
				description,
				inputDescription,
				outputDescription,
				timeLimit,
				memoryLimit,
				tags
		);

		return ResponseEntity.ok(new SuccessResponse());
	}

	@Operation(summary = "문제 삭제하기")
	@DeleteMapping
	public ResponseEntity<SuccessResponse> deleteProblem(
			@Validated(OrderChecks.class) @RequestBody DeleteProblemRequest request
	) throws Exception {

		int problemId = request.getProblemId();

		service.deleteProblem(problemId);

		return ResponseEntity.ok(new SuccessResponse());
	}
}
