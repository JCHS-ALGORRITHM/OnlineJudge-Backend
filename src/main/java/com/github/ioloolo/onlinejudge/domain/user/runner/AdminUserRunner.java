package com.github.ioloolo.onlinejudge.domain.user.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.ioloolo.onlinejudge.domain.user.entity.User;
import com.github.ioloolo.onlinejudge.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class AdminUserRunner implements CommandLineRunner {

	private final UserRepository repository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {

		repository.findByUsername("admin")
				.ifPresentOrElse((user) -> {}, () -> {
					User admin = User.builder()
							.username("admin")
							.password(passwordEncoder.encode("admin"))
							.realName("[이름 입력]")
							.schoolGrade(3)
							.schoolClass(99)
							.schoolId(99)
							.authority(User.Role.ROLE_USER)
							.authority(User.Role.ROLE_ADMIN)
							.build();

					repository.save(admin);

					log.info("관리자 계정(ID: admin, pw: admin)이 생성되었습니다. 내 정보에서 정보 수정 후 사용해주세요.");
				});
	}
}
