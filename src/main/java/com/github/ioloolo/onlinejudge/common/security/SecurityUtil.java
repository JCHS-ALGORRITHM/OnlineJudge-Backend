package com.github.ioloolo.onlinejudge.common.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.ioloolo.onlinejudge.domain.user.entity.User;

@Component
public class SecurityUtil {

	public User getUser() {

		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
