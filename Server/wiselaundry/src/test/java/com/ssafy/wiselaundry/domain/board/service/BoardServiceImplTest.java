package com.ssafy.wiselaundry.domain.board.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepositorySpp;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
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

import java.util.List;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    BoardServiceImpl boardService;

    @Mock
    BoardRepository boardRepository;

    MultipartHttpServletRequest request;

    BoardRepositorySpp boardRepositorySpp;

    @Mock
    BoardImgService boardImgService;

    @Mock
    UserService userService;

    @Before
    void testSetup() {
        boardService = new BoardServiceImpl(boardRepository, boardRepositorySpp, boardImgService, userService);
    }

    @Test
    @DisplayName("Board를 생성하고 제대로 생성되었는지 확인하는 메서드")
    void boardCreate() {
        BoardCreateReq boardCreateReq = BoardCreateReq.builder()
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .userId(1)
                .build();
        
        boardService.boardCreate(boardCreateReq, request);

        List<Board> boardList = boardService.boardSearchAll(3,999);

        Assertions.assertEquals(3, boardList.size());
    }

    @Test
    @DisplayName("제대로 의존성 관계가 설정되었는지 확인하는 테스트")
    void boardSearchAll() {
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

        List<Board> boardList = boardService.boardSearchAll(3,999);
        Assertions.assertNotEquals(0, boardList.size());
    }

    @Test
    void boardOrderByViewDesc() {
    }

    @Test
    void boardOrderByViewDescLast() {
    }

    @Test
    void searchLast() {
    }

    @Test
    void searchByKeywordLast() {
    }

    @Test
    void boardSearchById() {
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