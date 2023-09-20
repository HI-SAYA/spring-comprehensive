package com.ohgiraffers.comprehensive.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AuthorityDTO {
    private int code;
    private String name;
    private String desc;
}
