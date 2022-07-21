package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.domain.board.request.CommentCreateReq;
import com.ssafy.wiselaundry.global.model.Exception.NotExistException;

public interface CommentsService {
//    List<Comments> commentSearchByBoard(Board Board);
    Comments commentSearchById(int commentId) throws NotExistException;
    Comments commentCreate(CommentCreateReq commentCreateReq);
    int commentDelete(int commentId) throws NotExistException;
}
