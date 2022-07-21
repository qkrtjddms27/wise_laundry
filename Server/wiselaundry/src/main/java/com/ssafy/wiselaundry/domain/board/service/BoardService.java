package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.board.request.BoardUpdateReq;
import com.ssafy.wiselaundry.global.model.Exception.NotExistException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {
    Board searchLast();
    Board searchByKeywordLast(String keyword);
    Board boardFindById(int boardId);
    Board boardOrderByViewDescLast();

    int boardCreate(BoardCreateReq body, MultipartHttpServletRequest request);

    int boardUpdate(BoardUpdateReq body, MultipartHttpServletRequest request) throws NotExistException;
    int boardDelete(int boardId) throws NotExistException;
    int boardViewIncrement(int boardId) throws NotExistException;

    List<Board> boardSearchAll(int size, int boardId);
    List<Board> boardOrderByViewDesc(int size, int offset);
    List<Board> boardSearchKeyword(String keyword, int size, int boardId);
}
