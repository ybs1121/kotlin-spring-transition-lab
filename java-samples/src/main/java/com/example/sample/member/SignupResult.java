package com.example.sample.member;

/**
 * [Day 2] sealed interface 변환 과제
 *
 * 변환 포인트:
 * - Result 클래스 → sealed interface
 * - static factory method → data object / data class
 *
 * 힌트:
 * sealed interface SignupResult {
 *     data object Success : SignupResult
 *     data class Fail(val message: String) : SignupResult
 * }
 */
public class SignupResult {

    private final boolean success;
    private final String message;

    private SignupResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static SignupResult success() {
        return new SignupResult(true, "회원가입 성공");
    }

    public static SignupResult fail(String message) {
        return new SignupResult(false, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
