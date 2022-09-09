package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardImgRepository;
import com.ssafy.wiselaundry.domain.board.exception.BoardImgNotFoundException;
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

}
