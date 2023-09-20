package com.ohgiraffers.comprehensive.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberRoleDTO {
    private int memberNo;
    private int authorityCode;
    private AuthorityDTO authority;
}
