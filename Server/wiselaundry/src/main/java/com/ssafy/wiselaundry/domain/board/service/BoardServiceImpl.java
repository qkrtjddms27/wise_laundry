package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.response.BoardSearchAllRes;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class BoardServiceImpl implements BoardService{

    @Autowired
    BoardRepository boardRepository;

    @Override
    public List<BoardSearchAllRes> findBoardAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardSearchAllRes> boardSearchAllResList = new ArrayList<>();

        for (Board board: boardList) {
            boardSearchAllResList.add(BoardSearchAllRes.of(board));
        }

        return boardSearchAllResList;
    }
}
