package com.example.sample.post;

/**
 * [Day 1] DTO 변환 과제
 *
 * 변환 포인트:
 * - memberId nullable 여부 고민
 * - data class 변환
 */
public class PostCreateRequest {

    private Long memberId;
    private String title;
    private String content;

    public PostCreateRequest(Long memberId, String title, String content) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
}
