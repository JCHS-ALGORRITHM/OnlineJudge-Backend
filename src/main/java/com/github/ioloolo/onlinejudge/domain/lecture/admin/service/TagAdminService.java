package com.github.ioloolo.onlinejudge.domain.lecture.admin.service;

import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.common.exception.Exceptions;
import com.github.ioloolo.onlinejudge.domain.lecture.entity.Tag;
import com.github.ioloolo.onlinejudge.domain.lecture.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagAdminService {

	private final TagRepository repository;

	public void createTag(String rawTag) throws Exception {

		if (repository.existsByTag(rawTag)) {
			throw Exceptions.TAG_EXISTS.exception();
		}

		Tag tag = Tag.builder().tag(rawTag).build();

		repository.save(tag);
	}

	public void changeTag(String tagId, String rawNewTag) throws Exception {

		if (!repository.existsById(tagId)) {
			throw Exceptions.TAG_NOT_EXISTS.exception();
		}

		Tag tag = repository.findById(tagId).orElseThrow();
		tag.setTag(rawNewTag);
		repository.save(tag);
	}

	public void deleteTag(String tagId) throws Exception {

		if (!repository.existsById(tagId)) {
			throw Exceptions.TAG_NOT_EXISTS.exception();
		}

		repository.deleteById(tagId);
	}
}
