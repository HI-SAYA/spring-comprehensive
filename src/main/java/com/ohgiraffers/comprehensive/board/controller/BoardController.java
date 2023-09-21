package com.ohgiraffers.comprehensive.board.controller;

import com.ohgiraffers.comprehensive.board.dto.BoardDTO;
import com.ohgiraffers.comprehensive.board.dto.ReplyDTO;
import com.ohgiraffers.comprehensive.board.service.BoardService;
import com.ohgiraffers.comprehensive.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public String getBoardList(@RequestParam(defaultValue = "1") int page, // 게시판 첫 페이지로
                               @RequestParam(required = false) String searchCondition, // 게시판 검색하는 상황에 쓰임/ 옵셔널한 파라미터
                               @RequestParam(required = false) String searchValue, // 검색 조건 + 검색 값
                               Model model) {  // view 반환

        log.info("boardList page : {}", page);
        log.info("boardList searchCondition : {}", searchCondition);
        log.info("boardList searchValue : {}", searchValue);
        // 검색 조건과 값은 하나로 묶어주는 게 좋기 때문에 Map 사용

        Map<String, String> searchMap = new HashMap<>();
        searchMap.put("searchCondition", searchCondition);
        searchMap.put("searchValue", searchValue);

        Map<String, Object> boardListAndPaging = boardService.selectBoardList(searchMap, page); // 우리가 요청한 페이지가 몇 페이지인지
        // List<BoardDTO>
        model.addAttribute("paging", boardListAndPaging.get("paging"));
        model.addAttribute("boardList", boardListAndPaging.get("boardList"));

        return "board/boardList";
    }

    @GetMapping("/detail")
    public String getBoardDetail(@RequestParam Long no, Model model) {

        BoardDTO boardDetail = boardService.selectBoardDetail(no);
        log.info("boardDetail : {}", boardDetail);
        model.addAttribute("board", boardDetail);

        return "board/boardDetail";
    }

    @PostMapping("/registReply")
    public ResponseEntity<String> registReply(@RequestBody ReplyDTO registReply,
                                              @AuthenticationPrincipal MemberDTO member) {

        registReply.setWriter(member);
        log.info("registReply : {}", registReply);

        boardService.registReply(registReply);

        return ResponseEntity.ok("댓글 등록 완료");
    }

    @GetMapping("/loadReply")
    public ResponseEntity<List<ReplyDTO>> loadReply(ReplyDTO loadReply) {

        log.info("loadReply refBoardNo : {}", loadReply.getRefBoardNo());

        List<ReplyDTO> replyList = boardService.loadReply(loadReply);

        log.info("loadReply replyList : {}", replyList);

        return ResponseEntity.ok(replyList);
    }

    @PostMapping("/removeReply")
    public ResponseEntity<String> removeReply(@RequestBody ReplyDTO removeReply) {

        log.info("removeReply no : {}", removeReply.getNo());

        boardService.removeReply(removeReply);

        return ResponseEntity.ok("댓글 삭제 완료");

    }

    @GetMapping("/regist")
    public String getRegistPage() {
        return "board/boardRegist";
    }


    /* boardRegist.html과 BoardMapper.xml을 참조하여
     * 게시글 삽입이 되도록 구현 */

    @PostMapping("/regist")
    public String registBoard(BoardDTO board, @AuthenticationPrincipal MemberDTO member) {

        board.setWriter(member);
        log.info("registBoard board : {}", board);

        boardService.registBoard(board);

        return "redirect:/board/list";
    }

}