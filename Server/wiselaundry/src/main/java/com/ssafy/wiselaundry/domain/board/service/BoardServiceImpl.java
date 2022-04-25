package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardImgRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.board.request.BoardUpdateReq;
import com.ssafy.wiselaundry.domain.board.response.BoardSearchAllRes;
import com.ssafy.wiselaundry.domain.board.response.BoardSearchDetailRes;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.db.repository.UserRepository;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{
    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardImgService boardImgService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Board> boardSearchAll() {


        return boardRepository.findAll();
    }

    @Override
    public Board boardSearchById(int boardId) {
        return boardRepository.findById(boardId).get();
    }

    /**
     * MySQL은 sequence 전략 불가능하여, Identify 전략을 사용
     * idnentify 전략을 사용할 경우, DB와 한번 더 통신하진 않음.
     * 단, create 할 때 마다 쿼리를 날리므로 지연 쓰기가 불가능하다.
     */
    @Override
    public void boardCreate(BoardCreateReq body) {
        /**
         * Todo userService로 변경해서 사용.
         */
        //        User user = userService.createUser(boardCreateReq.getUserId()).get();
        User user = userRepository.findByUserId(body.getUserId());

        Board board = Board.builder()
                .boardContent(body.getBoardContent())
                .boardName(body.getBoardName())
                .user(user)
                .build();
        boardRepository.save(board);

        board.setBoardImgs(boardImgUpdate(body.getBoardImgs(), board));
        boardRepository.save(board);
    }

    @Override
    public void boardUpdate(BoardUpdateReq body) {
        Board board = boardRepository.findById(body.getBoardId()).get();

        board.setBoardImgs(boardImgUpdate(body.getBoardImgs(), board));
        board.setBoardContent(body.getBoardContent());
        board.setBoardName(body.getBoardName());

        boardRepository.save(board);
    }

    @Override
    public void boardDelete(int boardId) {
        Board deleteBoard = boardRepository.findById(boardId).get();
        boardRepository.delete(deleteBoard);
    }

    public List<BoardImg> boardImgUpdate(List<String> boardImgString, Board board) {
        List<BoardImg> boardImgList = board.getBoardImgs();

        for(String img : boardImgString) {
            boardImgList.add(boardImgService.boardImgCreate(board, img));
        }

        board.setBoardImgs(boardImgList);

        return boardImgList;
    }
}
