package com.ssafy.wiselaundry.domain.board.response;

import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.global.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@ApiModel("게시글 상세 res")
public class BoardSearchDetailRes extends BaseResponseBody {
//    @Autowired
//    CommentsService commentsService;

    @ApiModelProperty(value = "게시글 id", required = true, example = "게시글 id")
    private long boardId;

    @ApiModelProperty(value = "유저 id", required = true, example = "게시글 작성자 ID 입니다.")
    private long userId;

    @ApiModelProperty(value = "유저 닉네임", required = true, example = "게시글 작성자 닉네임입니다.")
    private String userNick;

    @ApiModelProperty(value = "유저 프로필 사진", required = true)
    private String userImg;

    @ApiModelProperty(value = "kakao 이미지", required = true, example = "")
    private String kakaoImg;

    @ApiModelProperty(value = "게시글 제목", required = true, example = "게시글 제목입니다.")
    private String boardName;

    @ApiModelProperty(value = "게시글 사진", required = false, example = "board_img.jpg")
    private List<BoardImg> boardImgs;

    @ApiModelProperty(value = "게시글 내용", required = true, example = "게시글 내용입니다")
    private String boardContent;

    @ApiModelProperty(value = "게시글 날짜", required = true, example = "2020-01-23 13:33:33")
    private LocalDateTime boardDate;

    @ApiModelProperty(value = "댓글 정보 리스트", required = true)
    private List<CommentDetailRes> comments;

    @Builder
    public BoardSearchDetailRes(long boardId, long userId, String userNick, String userImg, String boardName,
                                List<BoardImg> boardImgs, String boardContent, LocalDateTime boardDate,
                                List<CommentDetailRes> comments, String kakaoImg,Integer statusCode, String message) {
        this.boardId = boardId;
        this.userId = userId;
        this.userNick = userNick;
        this.userImg = userImg;
        this.boardName = boardName;
        this.boardImgs = boardImgs;
        this.boardContent = boardContent;
        this.boardDate = boardDate;
        this.comments = comments;
        this.kakaoImg = kakaoImg;
        this.setMessage(message);
        this.setStatusCode(statusCode);
    }

    public static BoardSearchDetailRes of(Integer statusCode, String message, BoardSearchDetailRes body){

        return BoardSearchDetailRes.builder()
                .userId(body.getUserId())
                .userNick(body.getUserNick())
                .userImg(body.getUserImg())
                .boardId(body.getBoardId())
                .boardName(body.getBoardName())
                .boardImgs(body.getBoardImgs())
                .boardContent(body.getBoardContent())
                .boardDate(body.getBoardDate())
                .comments(body.getComments())
                .kakaoImg(body.getKakaoImg())
                .message(message)
                .statusCode(statusCode)
                .build();
    }
}
