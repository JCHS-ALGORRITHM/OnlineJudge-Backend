package com.github.ioloolo.onlinejudge.domain.lecture.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.ioloolo.onlinejudge.domain.lecture.entity.Tag;

@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

	Optional<Tag> findByTag(String tag);

	boolean existsByTag(String tag);
}
