package com.github.ioloolo.onlinejudge.domain.problem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.common.exception.Exceptions;
import com.github.ioloolo.onlinejudge.domain.problem.controller.payload.response.SimpleProblemDto;
import com.github.ioloolo.onlinejudge.domain.problem.entity.Problem;
import com.github.ioloolo.onlinejudge.domain.problem.repository.ProblemRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemService {

	private static final int PER_PAGE = 20;

	private final ProblemRepository repository;

	public int getMaxProblemListPage() {

		return (int) Math.ceil((double) repository.count() / PER_PAGE);
	}

	public List<SimpleProblemDto> getProblemList(int page) {

		return repository.findAll().stream()
				.skip((long) (page - 1) * PER_PAGE)
				.limit(PER_PAGE)
				.map(SimpleProblemDto::from)
				.toList();
	}

	public Problem getProblem(int problemId) throws Exception {

		if (!repository.existsByProblemId(problemId)) {
			throw Exceptions.PROBLEM_NOT_EXISTS.exception();
		}

		return repository.findByProblemId(problemId).orElseThrow();
	}
}
