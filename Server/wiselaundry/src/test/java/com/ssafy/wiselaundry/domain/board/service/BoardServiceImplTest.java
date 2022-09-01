package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepositorySpp;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.board.request.BoardUpdateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.db.repository.UserRepository;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Transactional(rollbackFor = Exception.class)
class BoardServiceImplTest {

    @Mock
    BoardRepository boardRepository;

    @Mock
    BoardRepositorySpp boardRepositorySpp;

    @Mock
    MultipartHttpServletRequest request;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    BoardServiceImpl boardService;

    @Test
    @DisplayName("boardCreate 메서드가 정상적으로 성공하는지 테스트")
    void boardCreateTest_1() {
        //given
        BoardCreateReq boardCreateReq = BoardCreateReq.builder()
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .userId(1L)
                .build();

        User user = User.builder()
                .userId(1L)
                .userEmail("test@mail.com")
                .userNick("NickName")
                .build();

        Board board = Board.builder()
                .boardId(1)
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .user(user)
                .boardDate(LocalDateTime.now())
                .build();

        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //then
        assertEquals(boardService.boardCreate(boardCreateReq, request), 0);
    }

    @Test
    @DisplayName("요청 받은 Req의 UserId로 Entity가 존재하지 않을 경우 Illegal Exception을 던지는지 테스트")
    void boardCreateTest_2() {
        //given
        BoardCreateReq boardCreateReq = BoardCreateReq.builder()
                .boardContent("1번 내용물 입니다.")
                .boardName("1번 게시글 제목입니다.")
                .userId(9999L)
                .build();


        // when
        when(userRepository.findById(9999L)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> boardService.boardCreate(boardCreateReq, request));
    }

    @Test
    @DisplayName("요청 받은 Req의 UserId로 Entity가 존재하지 않을 경우 Illegal Exception을 던지는지 테스트")
    void boardUpdateTest_1() {
        // given
        BoardUpdateReq req = BoardUpdateReq.builder()
                .boardContent("내용")
                .boardName("변경될 제목")
                .boardId(9999L)
                .build();

        // when
        when(boardRepository.findById(9999L)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> boardService.boardUpdate(req, request));
    }

    @Test
    void boardDelete() {
    }

    @Test
    void boardViewIncrement() {
        // given
        long testId = Integer.MAX_VALUE;
        // when
        when(boardRepository.findById(testId)).thenReturn(Optional.empty());

        // then
        assertThrows(IllegalArgumentException.class, () -> boardService.boardViewIncrement(testId));
    }

    @Test
    void boardSearchKeyword() {
    }
}