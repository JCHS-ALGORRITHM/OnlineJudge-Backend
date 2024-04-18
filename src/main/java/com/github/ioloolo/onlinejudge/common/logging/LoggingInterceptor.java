package com.github.ioloolo.onlinejudge.common.logging;

import java.util.function.BiConsumer;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

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

		int firstStatus = response.getStatus() / 100;

		BiConsumer<String, Object[]> logging = firstStatus == 5 ? log::error :
											   firstStatus == 4 ? log::warn :
											   log::info;

		logging.accept(
				"[Response] {} {} {} {} {}ms",
				new Object[] {
						request.getMethod(),
						request.getRequestURI(),
						request.getRemoteAddr(),
						response.getStatus(),
						elapsedTime
				}
		);
	}
}
