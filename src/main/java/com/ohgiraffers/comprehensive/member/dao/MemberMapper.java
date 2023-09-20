package com.ohgiraffers.comprehensive.member.dao;

import com.ohgiraffers.comprehensive.member.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    MemberDTO findByMemberId(String memberId);

    String selectMemberById(String memberId);

    int insertMember(MemberDTO member);

    int insertMemberRole();

    int updateMember(MemberDTO modifyMember);

    int deleteMember(MemberDTO removeMember);
}
