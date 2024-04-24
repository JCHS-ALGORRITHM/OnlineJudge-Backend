package com.github.ioloolo.onlinejudge.domain.problem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.onlinejudge.common.payload.response.Response;
import com.github.ioloolo.onlinejudge.common.validation.OrderChecks;
import com.github.ioloolo.onlinejudge.domain.problem.controller.payload.request.GetProblemListRequest;
import com.github.ioloolo.onlinejudge.domain.problem.controller.payload.request.GetProblemRequest;
import com.github.ioloolo.onlinejudge.domain.problem.controller.payload.response.SimpleProblemDto;
import com.github.ioloolo.onlinejudge.domain.problem.entity.Problem;
import com.github.ioloolo.onlinejudge.domain.problem.service.ProblemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "문제")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problem")
public class ProblemController {

	private final ProblemService service;

	@Operation(summary = "문제 최대 페이지")
	@GetMapping("/maxPage")
	public ResponseEntity<Response<Integer>> getMaxProblemListPage() {

		int maxPage = service.getMaxProblemListPage();

		return ResponseEntity.ok(Response.of(maxPage));
	}

	@Operation(summary = "문제 목록 가져오기")
	@GetMapping
	public ResponseEntity<Response<List<SimpleProblemDto>>> getProblemList(
			@Validated(OrderChecks.class) @RequestBody GetProblemListRequest request
	) {

		int page = request.getPage();

		List<SimpleProblemDto> problemList = service.getProblemList(page);

		return ResponseEntity.ok(Response.of(problemList));
	}

	@Operation(summary = "문제 조회하기")
	@PostMapping
	public ResponseEntity<Response<Problem>> getProblem(
			@Validated(OrderChecks.class) @RequestBody GetProblemRequest request
	) throws Exception {

		int problemId = request.getProblemId();

		Problem problem = service.getProblem(problemId);

		return ResponseEntity.ok(Response.of(problem));
	}
}
