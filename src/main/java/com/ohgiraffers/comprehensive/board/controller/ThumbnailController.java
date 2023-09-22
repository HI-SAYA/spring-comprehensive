package com.ohgiraffers.comprehensive.board.controller;

import com.ohgiraffers.comprehensive.board.dto.AttachmentDTO;
import com.ohgiraffers.comprehensive.board.dto.BoardDTO;
import com.ohgiraffers.comprehensive.board.service.BoardService;
import com.ohgiraffers.comprehensive.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/thumbnail")
public class ThumbnailController {

    @Value("${image.image-dir}")
    private String IMAGE_DIR;

    private final BoardService boardService;

    public ThumbnailController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/regist")
    public String getRegistPage(){
        return "thumbnail/thumbnailRegist";
    }

    @PostMapping("/regist")
    public String registThumbnail(BoardDTO board, List<MultipartFile> attachImage,
                                  @AuthenticationPrincipal MemberDTO member) {

        log.info("thumbnail board request : {}", board);
        log.info("thumbnail attachImage request : {}", attachImage);

        String fileUploadDir = IMAGE_DIR + "original";
        String thumbnailDir = IMAGE_DIR + "thumbnail";

        File dir1 = new File(fileUploadDir);
        File dir2 = new File(thumbnailDir);

        /* 디렉토리가 없을 경우 생성한다. */
        if(!dir1.exists() || !dir2.exists()) {
            dir1.mkdirs();
            dir2.mkdirs();
        }

        /* 업로드 파일에 대한 정보를 담을 리스트 */
        List<AttachmentDTO> attachmentList = new ArrayList<>();

        try {

            for(int i = 0; i < attachImage.size(); i++) {
                /* 첨부파일이 실제로 존재하는 경우에만 로직 수행 */
                if(attachImage.get(i).getSize() > 0) {

                    String originalFileName = attachImage.get(i).getOriginalFilename();
                    log.info("originalFileName : {}", originalFileName);

                    String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String savedFileName = UUID.randomUUID() + ext;
                    log.info("savedFileName : {}", savedFileName);

                    /* 서버의 설정 디렉토리에 파일 저장하기 */
                    attachImage.get(i).transferTo(new File(fileUploadDir + "/" + savedFileName));

                    /* DB에 저장할 파일의 정보 처리 */
                    AttachmentDTO fileInfo = new AttachmentDTO();
                    fileInfo.setOriginalName(originalFileName);
                    fileInfo.setSavedName(savedFileName);
                    fileInfo.setSavePath("/upload/original/");

                    if(i == 0) {
                        fileInfo.setFileType("TITLE");
                        /* 대표 사진에 대한 썸네일을 가공해서 저장한다. */
                        Thumbnails.of(fileUploadDir + "/" + savedFileName).size(300, 300)
                                .toFile(thumbnailDir + "/thumbnail_" + savedFileName);
                        fileInfo.setThumbnailPath("/upload/thumbnail/thumbnail_" + savedFileName);
                    } else {
                        fileInfo.setFileType("BODY");
                    }
                    attachmentList.add(fileInfo);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        log.info("attachmentList : {}", attachmentList);

        board.setAttachmentList(attachmentList);
        board.setWriter(member);

        boardService.registThumbnail(board);

        return "redirect:/thumbnail/list";
    }

    @GetMapping("/list")
    public String selectThumbnailList(@RequestParam(defaultValue = "1") int page, Model model) {

        Map<String, Object> thumbnailListAndPaging = boardService.selectThumbnailList(page);
        model.addAttribute("paging", thumbnailListAndPaging.get("paging"));
        model.addAttribute("thumbnailList", thumbnailListAndPaging.get("thumbnailList"));

        return "thumbnail/thumbnailList";
    }

    @GetMapping("/detail")
    public String selectThumbnailDetail(Long no, Model model){

        log.info("thumbnail no : {}", no);

        BoardDTO thumbnail = boardService.selectThumbnailDetail(no);
        log.info("thumbnail : {}", thumbnail);

        model.addAttribute("thumbnail", thumbnail);

        return "thumbnail/thumbnailDetail";
    }











}