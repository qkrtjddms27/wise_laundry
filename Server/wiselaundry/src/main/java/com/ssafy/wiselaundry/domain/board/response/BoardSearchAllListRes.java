package com.ssafy.wiselaundry.domain.board.response;

import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@ApiModel(value = "BoardSearchAllListRes", description = "모든 게시판 조회")
public class BoardSearchAllListRes extends BaseResponseBody {
    @ApiModelProperty(value = "list 형태로 반환")
    List<BoardSearchAllRes> list;

    @ApiModelProperty(value = "마지막인지 구분하는 boolean")
    boolean endFlag;

    public static BoardSearchAllListRes of(Integer statusCode, String message, List<BoardSearchAllRes> list,
                                           boolean endFlag){
        BoardSearchAllListRes res = new BoardSearchAllListRes();
        res.setMessage(message);
        res.setStatusCode(statusCode);
        res.list = list;
        res.endFlag = endFlag;
        return res;
    }
}
