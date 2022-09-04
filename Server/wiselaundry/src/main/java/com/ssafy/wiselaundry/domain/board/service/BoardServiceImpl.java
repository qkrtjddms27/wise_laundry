package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardImgRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepositorySpp;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.board.request.BoardUpdateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.db.repository.UserRepository;
import com.ssafy.wiselaundry.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl {

    private final BoardRepository boardRepository;
    private final BoardRepositorySpp boardRepositorySpp;
    private final BoardImgRepository boardImgRepository;
    private final UserRepository userRepository;
    private final FileUtil fileUtil;

    public List<Board> boardSearchAll(int size, int boardId) {
        return boardRepository.findAll();
    }

    public List<Board> boardOrderByViewDesc(int size, int boardId) {
        return boardRepositorySpp.boardViewOrderByDesc(size, boardId);
    }

    public Board boardOrderByViewDescLast() {
        return boardRepositorySpp.boardViewOrderByDescLast();
    }

    public Board searchLast() {
        return boardRepositorySpp.boardSearchLast();
    }

    public Board searchByKeywordLast(String keyword) {
        return boardRepositorySpp.boardSearchByKeywordLast(keyword);
    }

    public Board boardFindById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 게시글 ID입니다. : " + boardId)
        );
    }


    public long boardCreate(BoardCreateReq body, MultipartHttpServletRequest request) {
        User user = userRepository.findById(body.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 회원 ID입니다. : " + body.getUserId())
        );

        Board board = Board.toEntity(body, user);

        board = boardRepository.save(board);
        boardFileUpload(request,board);

        return board.getBoardId();
    }



    @Transactional(rollbackOn = Exception.class)
    public Board boardUpdate(BoardUpdateReq body, MultipartHttpServletRequest request) {
//        수정할 board 객체 가져오기

        Board board = boardRepository.findById(body.getBoardId()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 게시글 ID입니다. : " + body.getBoardId())
        );

//        boardImg 다루는 곳 새롭게 추가.

        fileUtil.fileUpload(request, "board");
//        삭제 이미지
        for (String boardImgName : body.getDeleteImgs()) {
            BoardImg boardImg;

            boardImg = boardImgRepository.findByBoardImg(boardImgName).orElseThrow(
                    () -> new IllegalArgumentException("존재 하지 않는 게시글 이미지 이름입니다. : " + body.getBoardId())
            );

            boardImgRepository.delete(boardImg);
        }

        // 내용 수정.
        return boardRepository.save(board.updateNameAndContent(body.getBoardName(), body.getBoardContent()));
    }

    public int boardDelete(Long boardId) {
        Board deleteBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 게시글 ID입니다. : " + boardId)
        );

        boardRepository.delete(deleteBoard);

        return 1;
    }

    @Transactional(rollbackOn = Exception.class)
    public Board boardViewIncrement(long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 게시글 ID입니다. : " + boardId)
        );

        return board.increaseView();
    }

    public List<Board> boardSearchKeyword(String keyword, int size, int boardId) {

        return boardRepositorySpp.boardSearchByKeyword(keyword, size, boardId);
    }

    /**
     * 서버에 파일을 생성하고, 객체를 생성하고 매핑.
     * @param fileRequest
     * @param board
     * @return
     */
    private List<BoardImg> boardFileUpload(MultipartHttpServletRequest fileRequest, Board board) {
        List<String> urlList = fileUtil.fileUpload(fileRequest, "board");
        List<BoardImg>  boardImgList = new ArrayList<>();

        for (String url : urlList) {
            boardImgList.add(boardImgRepository.save(BoardImg.builder()
                    .board(board)
                    .boardImg(url)
                    .build()));
        }

        return boardImgList;
    }

    private int boardImgDelete(String imgUrl) {
        BoardImg boardImg = boardImgRepository.findByBoardImg(imgUrl).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 게시글 이미지입니다. : " + imgUrl)
        );

        boardImgRepository.delete(boardImg);
        /**
         * todo : 파일 URL로 삭제하는 부분도 같이 처리해야됨.
         */
//        boardImgDelete(boardImgService.findById(Integer.parseInt(boardImg)).getBoardImgId());

        return 1;
    }
}
