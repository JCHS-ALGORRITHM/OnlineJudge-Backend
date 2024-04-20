package com.github.ioloolo.onlinejudge.domain.problem.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.ioloolo.onlinejudge.domain.problem.entity.Problem;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {

	Optional<Problem> findByProblemId(int problemId);

	boolean existsByProblemId(int problemId);
	boolean existsByName(String name);

	void deleteByProblemId(int problemId);
}
