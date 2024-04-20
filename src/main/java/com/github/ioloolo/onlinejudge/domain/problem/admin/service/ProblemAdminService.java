package com.github.ioloolo.onlinejudge.domain.problem.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.common.exception.Exceptions;
import com.github.ioloolo.onlinejudge.domain.problem.entity.Problem;
import com.github.ioloolo.onlinejudge.domain.problem.repository.ProblemRepository;
import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemAdminService {

	private final ProblemRepository repository;

	public void createProblem(
			int problemId,
			String name,
			String description,
			String inputDescription,
			String outputDescription,
			long timeLimit,
			long memoryLimit,
			List<Tag> tags
	) throws Exception {

		if (repository.existsByProblemId(problemId)) {
			throw Exceptions.PROBLEM_ID_EXISTS.exception();
		}

		if (repository.existsByName(name)) {
			throw Exceptions.PROBLEM_NAME_EXISTS.exception();
		}

		Problem problem = Problem.builder()
				.problemId(problemId)
				.name(name)
				.description(description)
				.inputDescription(inputDescription)
				.outputDescription(outputDescription)
				.timeLimit(timeLimit)
				.memoryLimit(memoryLimit)
				.tags(tags)
				.build();

		repository.save(problem);
	}

	public void changeProblem(
			int previousProblemId,
			int problemId,
			String name,
			String description,
			String inputDescription,
			String outputDescription,
			long timeLimit,
			long memoryLimit,
			List<Tag> tags
	) throws Exception {

		if (!repository.existsByProblemId(previousProblemId)) {
			throw Exceptions.PROBLEM_NOT_EXISTS.exception();
		}

		if (repository.existsByProblemId(problemId)) {
			throw Exceptions.PROBLEM_ID_EXISTS.exception();
		}

		if (repository.existsByName(name)) {
			throw Exceptions.PROBLEM_NAME_EXISTS.exception();
		}

		Problem problem = repository.findByProblemId(problemId).orElseThrow();
		problem.setProblemId(problemId);
		problem.setName(name);
		problem.setDescription(description);
		problem.setInputDescription(inputDescription);
		problem.setOutputDescription(outputDescription);
		problem.setTimeLimit(timeLimit);
		problem.setMemoryLimit(memoryLimit);
		problem.setTags(tags);
		repository.save(problem);
	}

	public void deleteProblem(int problemId) throws Exception {

		if (!repository.existsByProblemId(problemId)) {
			throw Exceptions.PROBLEM_NOT_EXISTS.exception();
		}

		repository.deleteByProblemId(problemId);
	}
}
