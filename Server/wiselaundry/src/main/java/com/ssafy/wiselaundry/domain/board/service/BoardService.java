package com.ssafy.wiselaundry.domain.board.service;


import com.ssafy.wiselaundry.domain.board.response.BoardSearchAllRes;

import java.util.List;

public interface BoardService {
    List<BoardSearchAllRes> findBoardAll();
}
