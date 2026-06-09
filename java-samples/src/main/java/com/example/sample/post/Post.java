package com.example.sample.post;

/**
 * [Week 2] Post 도메인
 */
public class Post {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private PostStatus status;

    private Post(Long id, Long memberId, String title, String content, PostStatus status) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public static Post create(Long memberId, String title, String content) {
        if (memberId == null) {
            throw new IllegalArgumentException("작성자 정보는 필수입니다.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        return new Post(null, memberId, title, content, PostStatus.PUBLISHED);
    }

    public void delete() {
        if (this.status == PostStatus.DELETED) {
            throw new IllegalStateException("이미 삭제된 게시글입니다.");
        }
        this.status = PostStatus.DELETED;
    }

    public Long getId() { return id; }
    public Long getMemberId() { return memberId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public PostStatus getStatus() { return status; }
}
