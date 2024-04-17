package com.github.ioloolo.onlinejudge.common.security;

import java.io.IOException;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.ioloolo.onlinejudge.domain.user.repository.UserRepository;

import jakarta.annotation.Nonnull;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final InnerUserService innerUserService;
	private final UserRepository repository;

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	protected void doFilterInternal(@Nonnull HttpServletRequest request,
		@Nonnull HttpServletResponse response,
		@Nonnull FilterChain filterChain) throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		String accessToken = authorization != null && authorization.startsWith("Bearer ") ? authorization.substring(7) : null;

		if (jwtUtil.validate(accessToken)) {
			String username = jwtUtil.getUsernameFromAccessToken(accessToken);

			UserDetails userDetails = innerUserService.loadUserByUsername(username);
			AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private Authentication verifyAndSaveAuthentication(@Nonnull HttpServletRequest request, String username) {

		UserDetails userDetails = innerUserService.loadUserByUsername(username);
		AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return authentication;
	}

	public Filter getFilter() {

		return this;
	}
}
