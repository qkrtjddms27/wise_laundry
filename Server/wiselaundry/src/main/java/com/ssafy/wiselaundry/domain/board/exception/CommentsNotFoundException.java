package com.ssafy.wiselaundry.domain.board.exception;

public class CommentsNotFoundException extends RuntimeException {
    private long id;

    public CommentsNotFoundException(long id) {
        super("존재 하지 않는 댓글 ID입니다. : " + id);
        this.id = id;
    }
}
