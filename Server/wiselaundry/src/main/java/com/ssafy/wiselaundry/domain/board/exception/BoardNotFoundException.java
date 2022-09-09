package com.ssafy.wiselaundry.domain.board.exception;

public class BoardNotFoundException extends RuntimeException{
    private long id;

    public BoardNotFoundException(long id) {
        super("존재 하지 않는 게시글 ID입니다. : " + id);
        this.id = id;
    }
}
