package com.github.ioloolo.onlinejudge.domain.user.admin.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.common.exception.Exceptions;
import com.github.ioloolo.onlinejudge.domain.user.entity.User;
import com.github.ioloolo.onlinejudge.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAdminService {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	public List<User> getUserList() {

		return repository.findAll();
	}

	public void changePassword(String username, String newPassword) throws Exception {

		if (!repository.existsByUsername(username)) {
			throw Exceptions.USER_NOT_FOUND.exception();
		}

		User user = repository.findByUsername(username).orElseThrow();
		user.setPassword(passwordEncoder.encode(newPassword));
		repository.save(user);
	}

	public void changeInfo(String username, String realName, int schoolGrade, int schoolClass, int schoolId) throws Exception {

		if (!repository.existsByUsername(username)) {
			throw Exceptions.USER_NOT_FOUND.exception();
		}

		User user = repository.findByUsername(username).orElseThrow();

		if (repository.existsBySchoolGradeAndSchoolClassAndSchoolId(schoolGrade, schoolClass, schoolId)) {
			User user2 = repository
					.findBySchoolGradeAndSchoolClassAndSchoolId(schoolGrade, schoolClass, schoolId)
					.orElseThrow();

			if (!user2.getId().equals(user.getId())) {
				throw Exceptions.ALREADY_REGISTERED_INFO.exception();
			}
		}

		user.setRealName(realName);
		user.setSchoolGrade(schoolGrade);
		user.setSchoolClass(schoolClass);
		user.setSchoolId(schoolId);
		repository.save(user);
	}
}
