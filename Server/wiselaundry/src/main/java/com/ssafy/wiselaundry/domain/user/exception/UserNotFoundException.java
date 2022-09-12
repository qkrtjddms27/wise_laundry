package com.ssafy.wiselaundry.domain.user.exception;

public class UserNotFoundException extends RuntimeException {
    private long id;

    public UserNotFoundException(long id) {
        super("존재 하지 않는 회원 ID입니다. : " + id);
        this.id = id;
    }
}
