package com.ohgiraffers.comprehensive.board.dto;

import com.ohgiraffers.comprehensive.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {

    private Long no;
    private Integer type;
    private Long categoryCode;
    private CategoryDTO category;
    private String title;
    private String body;
    private Long writerMemberNo;
    private MemberDTO writer;
    private int count;
    private Date createdDate;
    private Date modifiedDate;
    private String status;
    private List<ReplyDTO> replyList;
    private List<AttachmentDTO> attachmentList;
}
