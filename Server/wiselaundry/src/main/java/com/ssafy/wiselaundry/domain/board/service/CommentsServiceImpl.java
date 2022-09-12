package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.CommentsRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.CommentsRepositorySpp;
import com.ssafy.wiselaundry.domain.board.exception.BoardNotFoundException;
import com.ssafy.wiselaundry.domain.board.exception.CommentsNotFoundException;
import com.ssafy.wiselaundry.domain.board.request.CommentCreateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.db.repository.UserRepository;
import com.ssafy.wiselaundry.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.xml.stream.events.Comment;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsServiceImpl {

    private final CommentsRepository commentsRepository;
    private final CommentsRepositorySpp commentsRepositorySpp;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public Comments commentSearchById(int commentId) {
        Comments comments;

        comments = commentsRepository.findById(commentId).orElseThrow(
                () -> new CommentsNotFoundException(commentId)
        );

        return comments;
    }

    public Comments commentCreate(CommentCreateReq body){
        User user = userRepository.findById(body.getUserId()).orElseThrow(
                () -> new UserNotFoundException(body.getUserId())
        );

        Board board = boardRepository.findById(body.getBoardId()).orElseThrow(
                () -> new BoardNotFoundException(body.getBoardId())
        );

        Comments comments = Comments.toEntity(body,user,board);

        commentsRepository.save(comments);
        return comments;
    }

    public int commentDelete(int commentId) {
        Comments deleteComments = commentsRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 댓글 ID입니다. : " + commentId)
        );

        commentsRepository.delete(deleteComments);

        return 1;
    }

    public List<Comments> findByBoardId(long boardId) {
        return commentsRepositorySpp.findByBoardId(boardId);
    }
}
