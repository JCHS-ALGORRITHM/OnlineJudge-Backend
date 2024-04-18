package com.github.ioloolo.onlinejudge.domain.lecture.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.domain.lecture.entity.Tag;
import com.github.ioloolo.onlinejudge.domain.lecture.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

	private final TagRepository repository;

	public List<Tag> getTagList() {

		return repository.findAll();
	}
}
