package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.domain.board.db.repository.CommentsRepository;
import com.ssafy.wiselaundry.domain.board.request.CommentCreateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import com.ssafy.wiselaundry.global.model.Exception.NotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService{

    private final CommentsRepository commentsRepository;
    private final UserService userService;
    private final BoardService boardService;

    @Override
    public Comments commentSearchById(int commentId) throws NotExistException {
        return commentsRepository.findById(commentId).orElseThrow(
                () -> new NotExistException("[commentDelete]commentId:"+commentId+"가 존재하지 않습니다.")
        );
    }

    @Override
    public Comments commentCreate(CommentCreateReq body) {
        User user = userService.findByUserId(body.getUserId());
        Board board = boardService.boardFindById(body.getBoardId());

        Comments comments = body.toEntity(body,user,board);
        commentsRepository.save(comments);
        return comments;
    }

    @Override
    public int commentDelete(int commentId) throws NotExistException {
        Comments deleteComments = commentsRepository.findById(commentId).orElseThrow(
                () -> new NotExistException("[commentDelete]commentId:"+commentId+"가 존재하지 않습니다.")
        );
        commentsRepository.delete(deleteComments);

        return 1;
    }
}
