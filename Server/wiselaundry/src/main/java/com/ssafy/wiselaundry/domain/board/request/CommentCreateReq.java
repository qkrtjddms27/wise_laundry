package com.ssafy.wiselaundry.domain.board.request;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentCreateReq {
    @ApiModelProperty(name = "user id", required = true, example = "38")
    private int userId;

    @ApiModelProperty(name = "게시판 id", required = true, example = "123")
    private int boardId;

    @ApiModelProperty(name = "", required = true)
    private String commentContent;

    public Comments toEntity(CommentCreateReq body, User user, Board board){
        return Comments.builder()
                .user(user)
                .board(board)
                .commentContent(body.commentContent)
                .commentDate(LocalDateTime.now())
                .build();
    }
}
