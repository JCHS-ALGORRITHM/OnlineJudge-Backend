package com.github.ioloolo.onlinejudge.common.logging;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
public class LoggingConfig implements WebMvcConfigurer {

	private final LoggingInterceptor interceptor;

	@Override
	public void addInterceptors(@NotNull InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}

	@Slf4j
	@Component
	private static class LoggingInterceptor implements HandlerInterceptor {

		private final String START_TIME = "start_time";

		@Override
		public boolean preHandle(@NonNull HttpServletRequest request,
								 @NonNull HttpServletResponse response,
								 @NonNull Object handler) {

			request.setAttribute(START_TIME, System.currentTimeMillis());

			return true;
		}

		@Override
		public void afterCompletion(@NonNull HttpServletRequest request,
							   @NonNull HttpServletResponse response,
							   @NonNull Object handler,
							   @Nullable Exception ex) {

			if (!request.getRequestURI().startsWith("/api")) {
				return;
			}

			long elapsedTime = System.currentTimeMillis() - (long) request.getAttribute(START_TIME);

			log.info("[Response] {} {} {} {} {}ms",
					 request.getMethod(),
					 request.getRequestURI(),
					 request.getRemoteAddr(),
					 response.getStatus(),
					 elapsedTime);
		}
	}
}
