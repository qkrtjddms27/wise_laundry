package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.domain.board.db.repository.CommentsRepository;
import com.ssafy.wiselaundry.domain.board.request.CommentCreateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsServiceImpl {

    private final CommentsRepository commentsRepository;
    private final UserService userService;
    private final BoardServiceImpl boardService;

    public Comments commentSearchById(int commentId) {
        Comments comments;

        comments = commentsRepository.findById(commentId).orElseThrow(
                EntityNotFoundException::new
        );

        return comments;
    }

    public Comments commentCreate(CommentCreateReq body) throws EntityExistsException {
        User user = userService.findByUserId(body.getUserId());
        Board board = boardService.boardFindById(body.getBoardId());

        Comments comments = body.toEntity(body,user,board);
        commentsRepository.save(comments);
        return comments;
    }

    public int commentDelete(int commentId) {
        Comments deleteComments;

        deleteComments = commentsRepository.findById(commentId).orElseThrow(
                EntityNotFoundException::new
        );

        commentsRepository.delete(deleteComments);

        return 1;
    }
}
