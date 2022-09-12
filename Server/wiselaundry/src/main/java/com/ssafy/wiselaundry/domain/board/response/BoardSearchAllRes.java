package com.ssafy.wiselaundry.domain.board.response;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardSearchAllRes {
    @ApiModelProperty(value = "유저 ID", required = true, example = "122333")
    private Long userId;

    @ApiModelProperty(value = "유저 닉네임", required = true, example = "게시글 작성자 닉네임입니다.")
    private String userNick;

    @ApiModelProperty(value = "유저 이미지", required = true, example = "")
    private String userImg;

    @ApiModelProperty(value = "kakao 이미지", required = true, example = "")
    private String kakaoImg;

    @ApiModelProperty(value = "게시글 ID", required = true, example = "게시글 ID")
    private Long boardId;

    @ApiModelProperty(value = "조회수", required = true, example = "게시글 조회수")
    private long view;

    @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다.")
    private String boardName;

    @ApiModelProperty(value = "게시글 날짜", required = true, example = "2020-01-23 13:33:33")
    private LocalDateTime boardDate;

    @ApiModelProperty(value = "댓글 갯수", required = true, example = "3")
    private long commentCnt;

    public static BoardSearchAllRes boardToBoardSearchAllRes(Board board){

        return BoardSearchAllRes.builder()
                .userId(board.getUser().getUserId())
                .userNick(board.getUser().getUserNick())
                .userImg(board.getUser().getUserImg())
                .kakaoImg(board.getUser().getKakaoImg())
                .boardId(board.getBoardId())
                .boardName(board.getBoardName())
                .boardDate(board.getBoardDate())
                .view(board.getView())
//                .commentCnt(board.getComments().size())
                .build();
    }
}

