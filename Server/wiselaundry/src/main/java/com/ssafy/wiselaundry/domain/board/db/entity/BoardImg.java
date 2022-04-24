package com.ssafy.wiselaundry.domain.board.db.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "board")
@ApiModel(value = "BoardImg", description = "게시글 이미지")
public class BoardImg {
    @ApiModelProperty(value = "게시글 번호", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boardImgId")
    private int boardImgId;

    @ApiModelProperty(value = "게시판 정보", example = "")
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ApiModelProperty(value = "게시판 정보", example = "")
    @ManyToOne
    @JoinColumn(name = "boardImg")
    private String boardImg;
}
