package com.ssafy.wiselaundry.domain.board.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.wiselaundry.domain.board.db.entity.Comments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.wiselaundry.domain.board.db.entity.QComments.*;


@Repository
@RequiredArgsConstructor
public class CommentsRepositorySpp {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Comments> findByBoardId(long boardId) {
        return jpaQueryFactory
                .select(comments)
                .from(comments)
                .where(comments.board.boardId.eq(boardId))
                .fetch();
    }
}
