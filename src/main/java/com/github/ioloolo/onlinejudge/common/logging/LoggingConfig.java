package com.github.ioloolo.onlinejudge.common.logging;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoggingConfig implements WebMvcConfigurer {

	private final LoggingInterceptor interceptor;

	@Override
	public void addInterceptors(@NotNull InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}
}
