package com.ssafy.wiselaundry.domain.board.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comments")
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "comment_content")
    private String commentContent;

    @Column(name = "comment_dt")
    private LocalDateTime commentDate;

    @Builder
    Comments(int commentId, User user, Board board, String commentContent, LocalDateTime commentDate) {
        this.commentId = commentId;
        this.user = user;
        this.board = board;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
    }

}
