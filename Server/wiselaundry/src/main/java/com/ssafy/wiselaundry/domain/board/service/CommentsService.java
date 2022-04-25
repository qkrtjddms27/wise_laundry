package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.domain.board.request.CommentCreateReq;
import com.ssafy.wiselaundry.domain.board.request.CommentUpdateReq;
import com.ssafy.wiselaundry.domain.board.response.CommentDetailRes;

import javax.xml.stream.events.Comment;
import java.util.List;

public interface CommentsService {
    List<Comments> commentSearchByBoardId(int BoardId);
    Comments commentSearchById(int commentId);
    Comments commentCreate(CommentCreateReq commentCreateReq);
    void commentUpdate(CommentUpdateReq commentUpdateReq);
    void commentDelete(int commentId);
}
