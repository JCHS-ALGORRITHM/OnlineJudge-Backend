package com.github.ioloolo.onlinejudge.domain.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.common.exception.Exceptions;
import com.github.ioloolo.onlinejudge.common.security.JwtUtil;
import com.github.ioloolo.onlinejudge.common.security.SecurityUtil;
import com.github.ioloolo.onlinejudge.domain.user.controller.payload.response.UserInfoDto;
import com.github.ioloolo.onlinejudge.domain.user.entity.User;
import com.github.ioloolo.onlinejudge.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;
	private final SecurityUtil securityUtil;

	public UserInfoDto getSelfUser() {

		User user = securityUtil.getUser();

		return UserInfoDto.of(
				user.getUsername(),
				user.getRealName(),
				user.getSchoolGrade(),
				user.getSchoolClass(),
				user.getSchoolId(),
				user.isAdmin()
		);
	}

	public UserInfoDto getUser(String username) throws Exception {

		if (!repository.existsByUsername(username)) {
			throw Exceptions.USER_NOT_EXISTS.exception();
		}

		User user = repository.findByUsername(username).orElseThrow();

		return UserInfoDto.of(
				user.getUsername(),
				user.getRealName(),
				user.getSchoolGrade(),
				user.getSchoolClass(),
				user.getSchoolId(),
				user.isAdmin()
		);
	}

	public void register(String username, String password, String realName, int schoolGrade, int schoolClass, int schoolId) throws Exception {

		if (repository.existsByUsername(username)) {
			throw Exceptions.USERNAME_EXISTS.exception();
		}

		if (repository.existsBySchoolGradeAndSchoolClassAndSchoolId(schoolGrade, schoolClass, schoolId)) {
			throw Exceptions.USERINFO_EXISTS.exception();
		}

		User user = User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.realName(realName)
				.schoolGrade(schoolGrade)
				.schoolClass(schoolClass)
				.schoolId(schoolId)
				.authority(User.Role.ROLE_USER)
				.build();

		repository.save(user);
	}

	public String login(String username, String password) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtUtil.generateAccessToken(authentication);
	}

	public void changePassword(String password, String newPassword) throws Exception {

		User user = securityUtil.getUser();

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw Exceptions.PASSWORD_WRONG.exception();
		}

		user.setPassword(passwordEncoder.encode(newPassword));
		repository.save(user);
	}

	public void changeInfo(String realName, int schoolGrade, int schoolClass, int schoolId) throws Exception {

		User user = securityUtil.getUser();

		if (repository.existsBySchoolGradeAndSchoolClassAndSchoolId(schoolGrade, schoolClass, schoolId)) {
			User user2 = repository.findBySchoolGradeAndSchoolClassAndSchoolId(
					schoolGrade,
					schoolClass,
					schoolId
			).orElseThrow();

			if (!user2.getId().equals(user.getId())) {
				throw Exceptions.USERINFO_EXISTS.exception();
			}
		}

		user.setRealName(realName);
		user.setSchoolGrade(schoolGrade);
		user.setSchoolClass(schoolClass);
		user.setSchoolId(schoolId);
		repository.save(user);
	}
}
