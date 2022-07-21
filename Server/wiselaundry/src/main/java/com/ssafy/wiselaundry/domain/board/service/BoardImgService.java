package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.global.model.Exception.NotExistException;

import java.util.List;

public interface BoardImgService {
    List<BoardImg> boardImgSearchAll();
    BoardImg findById(int boardImgId) throws NotExistException;
    BoardImg boardImgCreate(Board board, String boardImg);
    int boardImgDelete(int boardId) throws NotExistException;
}
