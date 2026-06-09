package com.example.sample.post;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * [Week 2] Repository 변환 과제
 *
 * 변환 포인트:
 * - Optional → nullable 타입
 * - stream().filter().collect() → filter { }.map { } 등 Kotlin 컬렉션 함수
 */
@Repository
public class PostRepository {

    private final Map<Long, Post> store = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public Post save(Post post) {
        Long id = sequence.getAndIncrement();
        Post saved = Post.create(post.getMemberId(), post.getTitle(), post.getContent());
        store.put(id, saved);
        return saved;
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Post> findByMemberId(Long memberId) {
        return store.values().stream()
                .filter(p -> p.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }
}
