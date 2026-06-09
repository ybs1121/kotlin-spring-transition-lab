package com.example.sample.post;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * [Week 2] Controller 변환 과제
 *
 * 변환 포인트:
 * - @Valid + @RequestBody → Kotlin에서 @field: use-site target 함정
 * - @PathVariable Long id → Kotlin non-null 타입으로 직접 매핑
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostCreateRequest request) {
        PostResponse response = postService.create(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        PostResponse response = postService.getById(id);
        return ResponseEntity.ok(response);
    }
}
