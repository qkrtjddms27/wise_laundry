package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardImgServiceImpl {

    private final BoardImgRepository boardImgRepository;

    public List<BoardImg> boardImgSearchAll() {
        return boardImgRepository.findAll();
    }

    public BoardImg findById(int boardImgId) throws NoResultException {
        return boardImgRepository.findById(boardImgId).orElseThrow(
                NoResultException::new
        );
    }

    @Transactional(rollbackFor = {EntityExistsException.class, RollbackException.class})
    public BoardImg boardImgCreate(Board board, String boardImg) throws EntityExistsException, RollbackException {
        BoardImg img = BoardImg.builder()
                .board(board)
                .boardImg(boardImg)
                .build();

        boardImgRepository.save(img);
        return img;
    }

    public int boardImgDelete(int boardImgId) throws NoResultException {
       BoardImg boardImg = findById(boardImgId);
       boardImgRepository.delete(boardImg);

       return 1;
    }

}
