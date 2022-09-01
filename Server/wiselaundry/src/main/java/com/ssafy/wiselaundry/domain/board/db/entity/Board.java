package com.ssafy.wiselaundry.domain.board.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "board")
public class Board{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_content", length = 1500)
    private String boardContent;

    @Column(name = "board_dt")
    private LocalDateTime boardDate;

    @Column(name = "view", columnDefinition = "integer default 0")
    private int view;

    @JsonManagedReference
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comments> comments = new ArrayList<Comments>();

    @JsonManagedReference
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BoardImg> boardImgs = new ArrayList<BoardImg>();

    @Builder
    Board(long boardId, User user, String boardName, List<BoardImg> boardImgs, List<Comments> comments,
          LocalDateTime boardDate, String boardContent) {
        this.boardId = boardId;
        this.user = user;
        this.boardName = boardName;
        this.boardImgs = boardImgs;
        this.comments = comments;
        this.boardDate = boardDate;
        this.boardContent = boardContent;
    }

    public static Board toEntity(BoardCreateReq body, User user) {
        Board board = Board.builder()
                .boardContent(body.getBoardContent())
                .boardName(body.getBoardName())
                .user(user)
                .boardDate(LocalDateTime.now())
                .build();
        return board;
    }
}
