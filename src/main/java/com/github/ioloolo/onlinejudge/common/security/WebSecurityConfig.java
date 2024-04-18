package com.github.ioloolo.onlinejudge.common.security;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.ioloolo.onlinejudge.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final AuthTokenFilter authTokenFilter;
	private final AuthExceptionHandler exceptionHandler;
	private final InnerUserService innerUserService;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(innerUserService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		String ADMIN = User.Role.ROLE_ADMIN.raw();

		return http
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)

			.exceptionHandling(handler -> handler.authenticationEntryPoint(exceptionHandler))

			.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.addFilterBefore(authTokenFilter.getFilter(), UsernamePasswordAuthenticationFilter.class)
			.authenticationProvider(authenticationProvider())

			.authorizeHttpRequests(request -> {
				request.requestMatchers( "/api/user/auth").anonymous();

				request.requestMatchers(antMatcher("/api/**/admin/**")).hasRole(ADMIN);
				request.requestMatchers(antMatcher("/api/**/admin")).hasRole(ADMIN);

				request.requestMatchers(antMatcher("/api/**")).authenticated();

				request.anyRequest().permitAll();
			})

			.build();
	}
}
