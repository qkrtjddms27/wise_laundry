package com.ssafy.wiselaundry.domain.board.response;

import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("생성된 게시판 ID 리턴")
public class BoardCreateRes extends BaseResponseBody {
    @ApiModelProperty(value = "게시판 ID", required = true)
    private int boardId;
}
