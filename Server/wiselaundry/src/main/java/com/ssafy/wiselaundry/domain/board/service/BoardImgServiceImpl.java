package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardImgRepository;
import com.ssafy.wiselaundry.global.model.Exception.NotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardImgServiceImpl implements BoardImgService {

    private final BoardImgRepository boardImgRepository;

    @Override
    public List<BoardImg> boardImgSearchAll() {
        return boardImgRepository.findAll();
    }

    @Override
    public BoardImg findById(int boardImgId) throws NotExistException {
        return boardImgRepository.findById(boardImgId).orElseThrow(
                () -> new NotExistException("해당 boardId 와 일치하는 ["+boardImgId+"] 이미지를 찾을 수 없습니다.")
        );
    }

    @Override
    public BoardImg boardImgCreate(Board board, String boardImg) {
        BoardImg img = BoardImg.builder()
                .board(board)
                .boardImg(boardImg)
                .build();

        boardImgRepository.save(img);
        return img;
    }

    @Override
    public int boardImgDelete(int boardImgId) {
        try {
            BoardImg boardImg = findById(boardImgId);
            boardImgRepository.delete(boardImg);
        } catch (NotExistException e) {
            log.error(e.getMessage());
            return 0;
        }

        return 1;
    }

}
