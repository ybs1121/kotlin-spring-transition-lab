package com.example.sample.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * [Week 2] Service 변환 과제
 *
 * 변환 포인트:
 * - @Transactional → kotlin-spring allopen 없으면 프록시 생성 실패
 * - Optional.orElseThrow() → ?: throw
 */
@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponse create(PostCreateRequest request) {
        Post post = Post.create(
                request.getMemberId(),
                request.getTitle(),
                request.getContent()
        );

        Post saved = postRepository.save(post);
        return new PostResponse(
                saved.getId(),
                saved.getMemberId(),
                saved.getTitle(),
                saved.getContent(),
                saved.getStatus()
        );
    }

    public PostResponse getById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다. id=" + id));

        return new PostResponse(
                id,
                post.getMemberId(),
                post.getTitle(),
                post.getContent(),
                post.getStatus()
        );
    }
}
