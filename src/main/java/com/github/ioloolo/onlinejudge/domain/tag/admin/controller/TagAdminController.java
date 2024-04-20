package com.github.ioloolo.onlinejudge.domain.tag.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.onlinejudge.common.payload.response.SuccessResponse;
import com.github.ioloolo.onlinejudge.common.validation.OrderChecks;
import com.github.ioloolo.onlinejudge.domain.tag.admin.controller.payload.request.ChangeTagRequest;
import com.github.ioloolo.onlinejudge.domain.tag.admin.controller.payload.request.CreateTagRequest;
import com.github.ioloolo.onlinejudge.domain.tag.admin.controller.payload.request.DeleteTagRequest;
import com.github.ioloolo.onlinejudge.domain.tag.admin.service.TagAdminService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@io.swagger.v3.oas.annotations.tags.Tag(name = "[관리자] 문제 태그")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag/admin")
public class TagAdminController {

	private final TagAdminService service;

	@Operation(summary = "태그 만들기")
	@PutMapping
	public ResponseEntity<SuccessResponse> createTag(
			@Validated(OrderChecks.class) @RequestBody CreateTagRequest request
	) throws Exception {

		String tag = request.getTag();

		service.createTag(tag);

		return ResponseEntity.ok(new SuccessResponse());
	}

	@Operation(summary = "태그 수정하기")
	@PatchMapping
	public ResponseEntity<SuccessResponse> changeTag(
			@Validated(OrderChecks.class) @RequestBody ChangeTagRequest request
	) throws Exception {

		String tagId = request.getTagId();
		String newTag = request.getNewTag();

		service.changeTag(tagId, newTag);

		return ResponseEntity.ok(new SuccessResponse());
	}

	@Operation(summary = "태그 삭제하기")
	@DeleteMapping
	public ResponseEntity<SuccessResponse> deleteTag(
			@Validated(OrderChecks.class) @RequestBody DeleteTagRequest request
	) throws Exception {

		String tagId = request.getTagId();

		service.deleteTag(tagId);

		return ResponseEntity.ok(new SuccessResponse());
	}
}
