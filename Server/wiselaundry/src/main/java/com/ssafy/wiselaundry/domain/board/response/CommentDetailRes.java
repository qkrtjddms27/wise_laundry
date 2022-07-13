package com.ssafy.wiselaundry.domain.board.response;

import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@ApiModel("댓글 res")
public class CommentDetailRes extends BaseResponseBody{
    @ApiModelProperty(value = "유저 아이디", required = true, example = "pse1234")
    private int userId;

    @ApiModelProperty(value = "유저 이미지", required = true )
    private String userImg;

    @ApiModelProperty(value = "kakao 이미지", required = true, example = "")
    private String kakaoImg;

    @ApiModelProperty(value = "유저 닉네임", required = true, example = "댓글 작성자 닉네임입니다.")
    private String userNick;

    @ApiModelProperty(value = "댓글 아이디", required = true, example = "123")
    private int commentId;

    @ApiModelProperty(value = "댓글 내용", required = true, example = "댓글 내용입니다.")
    private String commentContent;

    @ApiModelProperty(value = "댓글 등록 날짜", required = true)
    private LocalDateTime commentDate;

    @Builder
    public CommentDetailRes(int userId, String userImg, String userNick, int commentId, String commentContent,
                            LocalDateTime commentDate,String kakaoImg ,Integer statusCode, String message){
        this.userId = userId;
        this.userImg = userImg;
        this.userNick = userNick;
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.kakaoImg = kakaoImg;
        this.setStatusCode(statusCode);
        this.setMessage(message);
    }

    public static CommentDetailRes of(CommentDetailRes body){
        return CommentDetailRes.builder()
                .userId(body.getUserId())
                .userImg(body.getUserImg())
                .userNick(body.getUserNick())
                .commentContent(body.getCommentContent())
                .commentDate(body.getCommentDate())
                .commentId(body.getCommentId())
                .kakaoImg(body.getKakaoImg())
                .build();
    }

    public static CommentDetailRes of(Integer statusCode, String message, CommentDetailRes body){
        return CommentDetailRes.builder()
                .userId(body.getUserId())
                .userImg(body.getUserImg())
                .userNick(body.getUserNick())
                .commentContent(body.getCommentContent())
                .commentDate(body.getCommentDate())
                .commentId(body.getCommentId())
                .kakaoImg(body.getKakaoImg())
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}
