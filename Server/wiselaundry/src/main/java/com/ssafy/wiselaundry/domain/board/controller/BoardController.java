package com.ssafy.wiselaundry.domain.board.controller;

import com.ssafy.wiselaundry.domain.board.response.BoardSearchAllRes;
import com.ssafy.wiselaundry.domain.board.service.BoardService;
import com.ssafy.wiselaundry.domain.user.request.UserLoginPostReq;
import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("유저 API")
@Slf4j
@RestController
@RequestMapping("/api/community")
public class BoardController {
    @Autowired
    BoardService boardService;

    @ApiOperation(value = "로그인", notes = "모든 게시글을 가져다 준다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = List.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<BoardSearchAllRes>> boardSearchAll() {
        List<BoardSearchAllRes> boardSearchAllRes = boardService.findBoardAll();
        return ResponseEntity.status(200).body(boardSearchAllRes);
    }
}
