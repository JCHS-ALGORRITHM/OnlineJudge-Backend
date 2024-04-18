package com.github.ioloolo.onlinejudge.domain.tag.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;
import com.github.ioloolo.onlinejudge.domain.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

	private final TagRepository repository;

	public List<Tag> getTagList() {

		return repository.findAll();
	}
}
