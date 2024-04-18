package com.github.ioloolo.onlinejudge.domain.tag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.ioloolo.onlinejudge.domain.tag.entity.Tag;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

	boolean existsByTag(String tag);
}
