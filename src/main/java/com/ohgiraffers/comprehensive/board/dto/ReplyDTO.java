package com.ohgiraffers.comprehensive.board.dto;

import com.ohgiraffers.comprehensive.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class ReplyDTO {

    private Long no;
    private Long refBoardNo;
    private String body;
    private MemberDTO writer;
    private String status;
    private Date createdDate;
}
