package com.github.ioloolo.onlinejudge.common.payload.response;

import java.util.Map;

public class SuccessResponse extends Response<Map<String, ?>>{

	public SuccessResponse() {

		super(Map.of("success", true));
	}
}
