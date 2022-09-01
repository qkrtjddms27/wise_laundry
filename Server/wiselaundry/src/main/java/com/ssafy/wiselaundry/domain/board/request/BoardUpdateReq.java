package com.ssafy.wiselaundry.domain.board.request;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Builder
public class BoardUpdateReq {
    @ApiModelProperty(value = "게시판 ID", required = true)
    private int boardId;

    @ApiModelProperty(value = "게시판 이름", required = true)
    private String boardName;

    @ApiModelProperty(value = "삭제할 이미지")
    private List<String> deleteImgs;

    @ApiModelProperty(value = "게시판 내용", required = true)
    private String boardContent;

    public static Board toEntity(BoardUpdateReq body, List<BoardImg> boardImgs) {

        return Board.builder()
                .boardId(body.getBoardId())
                .boardName(body.getBoardName())
                .boardContent(body.getBoardContent())
                .boardImgs(boardImgs)
                .build();
    }
}
