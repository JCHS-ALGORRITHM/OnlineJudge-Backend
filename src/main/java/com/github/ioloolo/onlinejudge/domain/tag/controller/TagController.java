package com.github.ioloolo.onlinejudge.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.onlinejudge.common.payload.response.Response;
import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;
import com.github.ioloolo.onlinejudge.domain.tag.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@io.swagger.v3.oas.annotations.tags.Tag(name = "문제 태그")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

	private final TagService service;

	@Operation(summary = "태그 목록 가져오기")
	@GetMapping
	public ResponseEntity<Response<List<Tag>>> getSelfUser() {

		List<Tag> tagList = service.getTagList();

		return ResponseEntity.ok(Response.of(tagList));
	}
}
