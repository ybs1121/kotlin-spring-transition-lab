package com.example.sample.post;

/**
 * [Day 1] enum 변환 과제
 */
public enum PostStatus {

    PUBLISHED("게시"),
    DELETED("삭제");

    private final String description;

    PostStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
