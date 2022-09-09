package com.ssafy.wiselaundry.domain.board.exception;

public class BoardImgNotFoundException extends RuntimeException{
    private String url;

    public BoardImgNotFoundException(String url) {
        super("존재 하지 않는 게시글이미지 URL입니다. : " + url);
        this.url = url;
    }
}
