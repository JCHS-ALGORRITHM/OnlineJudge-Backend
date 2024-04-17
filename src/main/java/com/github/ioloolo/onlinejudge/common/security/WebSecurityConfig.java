package com.github.ioloolo.onlinejudge.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

		return http
			.cors(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)

			.exceptionHandling(handler -> handler.authenticationEntryPoint(exceptionHandler))

			.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

			.addFilterBefore(authTokenFilter.getFilter(), UsernamePasswordAuthenticationFilter.class)
			.authenticationProvider(authenticationProvider())

			.authorizeHttpRequests(request -> {
				request.requestMatchers("/swagger-ui/**").permitAll();
				request.requestMatchers("/swagger-resources/**").permitAll();
				request.requestMatchers("/v3/api-docs/**").permitAll();

				request.requestMatchers(HttpMethod.POST, "/api/user/auth").anonymous();
				request.requestMatchers(HttpMethod.PUT, "/api/user/auth").anonymous();
				request.requestMatchers(HttpMethod.POST, "/api/user/admin/users").hasRole("ADMIN");
				request.requestMatchers(HttpMethod.PATCH, "/api/user/admin/password").hasRole("ADMIN");
				request.requestMatchers(HttpMethod.PATCH, "/api/user/admin/info").hasRole("ADMIN");

				request.anyRequest().authenticated();
			})

			.build();
	}
}
