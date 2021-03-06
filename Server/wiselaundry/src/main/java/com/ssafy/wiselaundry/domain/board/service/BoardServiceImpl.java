package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepositorySpp;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.board.request.BoardUpdateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.EntityNotFoundException;
import javax.swing.filechooser.FileSystemView;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl {

    private final BoardRepository boardRepository;
    private final BoardRepositorySpp boardRepositorySpp;
    private final BoardImgServiceImpl boardImgService;
    private final UserService userService;

    @Value("${app.fileupload.uploadDir}")
    private String uploadFolder;

    @Value("${app.fileupload.uploadPath}")
    private String uploadPath;

    public List<Board> boardSearchAll(int size, int boardId) {
//        return boardRepositorySpp.boardPagination(size, boardId);
//        return boardRepositorySpp.boardSearchAll();
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

    public Board boardFindById(int boardId) {
        return boardRepository.findById(boardId).get();
    }


    public int boardCreate(BoardCreateReq body, MultipartHttpServletRequest request) {
        User user = userService.findByUserId(body.getUserId());

        Board board = toBoard(body, user);

        boardRepository.save(board);

        List<BoardImg> boardImgList = fileRequestToBoardImg(request, board);

        if(boardImgList.size() != 0) {
            board.setBoardImgs(boardImgList);
        }
        boardRepository.save(board);

        return board.getBoardId();
    }

    public Board toBoard(BoardCreateReq body, User user) {
        Board board = Board.builder()
                .boardContent(body.getBoardContent())
                .boardName(body.getBoardName())
                .user(user)
                .boardDate(LocalDateTime.now())
                .build();
        return board;
    }

    public int boardUpdate(BoardUpdateReq body, MultipartHttpServletRequest request) {
//        ????????? board ?????? ????????????
        Board board = boardRepository.findById(body.getBoardId()).orElseThrow(
                () -> new EntityNotFoundException("???????????? ?????? ????????? ID?????????.")
        );

//        boardImg ????????? ??? ????????? ??????.
        List<BoardImg> addBoardImgList = fileRequestToBoardImg(request, board);

        for (BoardImg boardImg : addBoardImgList) {
            board.getBoardImgs().add(boardImg);
        }

//        ?????? ?????????
        for (String boardImgName : body.getDeleteImgs()) {
            BoardImg boardImg;

            try {
                boardImg = boardImgService.findById(Integer.parseInt(boardImgName));
                boardImgService.boardImgDelete(boardImg.getBoardImgId());
            } catch (Exception e){
                /**
                 * boardImgServiceImpl.findById??? ???????????? ????????? null??? ?????? Exception??? ?????????.
                 * message ?????? : "?????? boardId ??? ???????????? ["+boardImgId+"] ???????????? ?????? ??? ????????????."
                 */
                log.error(e.getMessage());
                continue;
            }

            board.getBoardImgs().remove(boardImg);
        }
//       ?????? ??????.
        board.setBoardContent(body.getBoardContent());
        board.setBoardName(body.getBoardName());

        boardRepository.save(board);

        return 1;
    }

    public int boardDelete(int boardId) throws EntityNotFoundException {
        Board deleteBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new EntityNotFoundException("")
        );
        boardRepository.delete(deleteBoard);
        return 1;
    }

    @Transactional
    public int boardViewIncrement(int boardId) throws EntityNotFoundException {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new EntityNotFoundException("")
        );
        board.setView(board.getView() + 1);
        return 1;
    }

    public List<Board> boardSearchKeyword(String keyword, int size, int boardId) {

        return boardRepositorySpp.boardSearchByKeyword(keyword, size, boardId);
    }

    /**
     * ????????? ????????? ????????????, ????????? ???????????? ??????.
     * @param fileRequest
     * @param board
     * @return
     */
    private List<BoardImg> fileRequestToBoardImg(MultipartHttpServletRequest fileRequest, Board board) {
        List<BoardImg> boardImgList = new ArrayList<>();

        List<MultipartFile> fileList = fileRequest.getFiles("file");
        String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
        File uploadDir = new File(uploadPath + uploadFolder + File.separator + "board");

        if (!uploadDir.exists()) uploadDir.mkdir();
        String recordFileUrl = "";

        for(MultipartFile file : fileList) {
            if(file.isEmpty())
                break;

            String fileName = file.getOriginalFilename();

            // ????????? ????????? ?????????????????? ?????????
            UUID uuid = UUID.randomUUID();

            // ?????? ?????????
            String extension = FilenameUtils.getExtension(fileName);

            String savingFileName =  uuid + "." + extension;

            File destFile = new File(uploadPath, uploadFolder + File.separator + "board" + File.separator + savingFileName);

            try{
                file.transferTo(destFile);
            } catch (IOException e){
                e.printStackTrace();
            }

            recordFileUrl = "board" + File.separator + savingFileName;
            boardImgList.add(boardImgService.boardImgCreate(board, recordFileUrl));
        }

        return boardImgList;
    }

    private int boardImgDelete(String boardImg) throws EntityNotFoundException {
        boardImgService.boardImgDelete(boardImgService.findById(Integer.parseInt(boardImg)).getBoardImgId());

        return 1;
    }
}
