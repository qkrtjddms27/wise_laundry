package com.ssafy.wiselaundry.domain.board.request;

import io.swagger.annotations.ApiModelProperty;

public class CommentCreateReq {
    @ApiModelProperty(name = "user id", required = true, example = "aaa")
    private int userId;

    @ApiModelProperty(name = "게시판 제목", required = true, example = "게시판 제목")
    private int boardId;

    @ApiModelProperty(name = "", required = true, example = "게시판 제목")
    private String commentName;

    @ApiModelProperty
    private String boardContent;
}
