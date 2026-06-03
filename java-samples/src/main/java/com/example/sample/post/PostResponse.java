package com.example.sample.post;

/**
 * [Day 1] DTO 변환 과제
 */
public class PostResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private PostStatus status;

    public PostResponse(Long id, Long memberId, String title, String content, PostStatus status) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public PostStatus getStatus() { return status; }
}
