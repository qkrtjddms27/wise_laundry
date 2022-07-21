package com.ssafy.wiselaundry.domain.board.service;

import com.ssafy.wiselaundry.domain.board.db.entity.Board;
import com.ssafy.wiselaundry.domain.board.db.entity.BoardImg;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepository;
import com.ssafy.wiselaundry.domain.board.db.repository.BoardRepositorySpp;
import com.ssafy.wiselaundry.domain.board.request.BoardCreateReq;
import com.ssafy.wiselaundry.domain.board.request.BoardUpdateReq;
import com.ssafy.wiselaundry.domain.user.db.entity.User;
import com.ssafy.wiselaundry.domain.user.service.UserService;
import com.ssafy.wiselaundry.global.model.Exception.NotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final BoardRepositorySpp boardRepositorySpp;
    private final BoardImgService boardImgService;
    private final UserService userService;

    @Value("${app.fileupload.uploadDir}")
    private String uploadFolder;

    @Value("${app.fileupload.uploadPath}")
    private String uploadPath;

    @Override
    public List<Board> boardSearchAll(int size, int boardId) {
//        return boardRepositorySpp.boardPagination(size, boardId);
//        return boardRepositorySpp.boardSearchAll();
        return boardRepository.findAll();
    }


    @Override
    public List<Board> boardOrderByViewDesc(int size, int boardId) {
        return boardRepositorySpp.boardViewOrderByDesc(size, boardId);
    }

    @Override
    public Board boardOrderByViewDescLast() {
        return boardRepositorySpp.boardViewOrderByDescLast();
    }

    @Override
    public Board searchLast() {
        return boardRepositorySpp.boardSearchLast();
    }

    @Override
    public Board searchByKeywordLast(String keyword) {
        return boardRepositorySpp.boardSearchByKeywordLast(keyword);
    }

    @Override
    public Board boardFindById(int boardId) {
        return boardRepository.findById(boardId).get();
    }


    @Override
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

    @Override
    public int boardUpdate(BoardUpdateReq body, MultipartHttpServletRequest request) throws NotExistException {
//        수정할 board 객체 가져오기
        Board board = boardRepository.findById(body.getBoardId()).orElseThrow(
                () -> new NotExistException("")
        );

//        boardImg 다루는 곳 새롭게 추가.
        List<BoardImg> addBoardImgList = fileRequestToBoardImg(request, board);

        for (BoardImg boardImg : addBoardImgList) {
            board.getBoardImgs().add(boardImg);
        }

//        삭제 이미지
        for (String boardImgName : body.getDeleteImgs()) {
            BoardImg boardImg;

            try {
                boardImg = boardImgService.findById(Integer.parseInt(boardImgName));
                boardImgService.boardImgDelete(boardImg.getBoardImgId());
            } catch (Exception e){
                /**
                 * boardImgServiceImpl.findById는 해당하는 객체가 null일 경우 Exception을 던진다.
                 * message 내용 : "해당 boardId 와 일치하는 ["+boardImgId+"] 이미지를 찾을 수 없습니다."
                 */
                log.error(e.getMessage());
                continue;
            }

            board.getBoardImgs().remove(boardImg);
        }

//       내용 수정.
        board.setBoardContent(body.getBoardContent());
        board.setBoardName(body.getBoardName());

        boardRepository.save(board);

        return 1;
    }

    @Override
    public int boardDelete(int boardId) throws NotExistException {
        Board deleteBoard = boardRepository.findById(boardId).orElseThrow(
                () -> new NotExistException("")
        );
        boardRepository.delete(deleteBoard);
        return 1;
    }

    @Override
    @Transactional
    public int boardViewIncrement(int boardId) throws NotExistException {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new NotExistException(""));
        board.setView(board.getView() + 1);
        return 1;
    }

    @Override
    public List<Board> boardSearchKeyword(String keyword, int size, int boardId) {

        return boardRepositorySpp.boardSearchByKeyword(keyword, size, boardId);
    }

    /**
     * 서버에 파일을 생성하고, 객체를 생성하고 매핑.
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

            // 파일명 중복을 방지하기위해 난수화
            UUID uuid = UUID.randomUUID();

            // 파일 확장자
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

    private int boardImgDelete(String boardImg) {
        try {
            File oldFile = new File("/images" + File.separator + boardImg);
            oldFile.delete();

            boardImgService.boardImgDelete(boardImgService.findById(Integer.parseInt(boardImg)).getBoardImgId());
        } catch (NotExistException e) {
            log.error(e.getMessage());
            return 0;
        }

        return 1;
    }
}
