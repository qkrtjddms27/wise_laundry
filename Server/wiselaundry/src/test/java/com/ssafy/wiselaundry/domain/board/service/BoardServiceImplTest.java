package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepositorySpp;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    MultipartHttpServletRequest request;

    @Mock
    BoardRepositorySpp boardRepositorySpp;

    @Mock
    BoardImgService boardImgService;

    @Mock
    UserService userService;

    @InjectMocks
    BoardServiceImpl boardService;
//    BoardServiceImpl boardService = new BoardServiceImpl(boardRepository, boardRepositorySpp, boardImgService, userService);

    @Test
    @DisplayName("request로 entity가 정상적으로 만들어지는지 테스트 ")
    void boardCreate_1() {
        //given
        BoardCreateReq boardCreateReq = BoardCreateReq.builder()
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .userId(1)
                .build();

        User user = User.builder()
                .userId(1)
                .userEmail("test@mail.com")
                .userNick("NickName")
                .build();

        Board board = Board.builder()
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .user(user)
                .boardDate(LocalDateTime.now())
                .build();

        when(userService.findByUserId(any())).thenReturn(user);

        // when
//        int boardId = boardService.boardCreate(boardCreateReq, request);
        Board toBoard = boardService.toBoard(boardCreateReq, user);
        //then
//        Assertions.assertEquals(boardId, 0);
        Assertions.assertEquals(toBoard.getBoardContent(), board.getBoardContent());
        Assertions.assertEquals(toBoard.getBoardName(), board.getBoardName());
    }

    @Test
    @DisplayName("제대로 의존성 관계가 설정되었는지 확인하는 테스트")
    void boardSearchAll() {
        //given
        BoardCreateReq boardCreateReq = BoardCreateReq.builder()
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .userId(1)
                .build();

        BoardCreateReq boardCreateReq2 = BoardCreateReq.builder()
                .boardContent("2번 내용물 입니다.")
                .boardName("2번 게시글 제목입니다.")
                .userId(1)
                .build();

        BoardCreateReq boardCreateReq3 = BoardCreateReq.builder()
                .boardContent("3번 내용물 입니다.")
                .boardName("3번 게시글 제목입니다.")
                .userId(1)
                .build();

        boardService.boardCreate(boardCreateReq, request);
        boardService.boardCreate(boardCreateReq2, request);
        boardService.boardCreate(boardCreateReq3, request);

        // when
        List<Board> boardList = boardService.boardSearchAll(3,999);

        // then
        Assertions.assertNotEquals(0, boardList.size());
    }

    @Test
    void boardUpdate() {
    }

    @Test
    void boardDelete() {
    }

    @Test
    void boardViewIncrement() {
    }

    @Test
    void boardSearchKeyword() {
    }
}