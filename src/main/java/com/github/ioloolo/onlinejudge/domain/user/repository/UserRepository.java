package com.github.ioloolo.onlinejudge.domain.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.ioloolo.onlinejudge.domain.user.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);
	Optional<User> findBySchoolGradeAndSchoolClassAndSchoolId(int schoolGrade, int schoolClass, int schoolId);

	boolean existsByUsername(String username);
	boolean existsBySchoolGradeAndSchoolClassAndSchoolId(int schoolGrade, int schoolClass, int schoolId);
}
