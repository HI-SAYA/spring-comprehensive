package com.ohgiraffers.comprehensive.member.service;

import com.ohgiraffers.comprehensive.member.dao.MemberMapper;
import com.ohgiraffers.comprehensive.member.dto.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService implements UserDetailsService {

    private final MemberMapper memberMapper;

    public AuthenticationService(MemberMapper memberMapper) { this.memberMapper = memberMapper; }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {

        log.info("memberId : {}", memberId);

        MemberDTO member = memberMapper.findByMemberId(memberId);

        log.info("member : {}", member);

        if(member == null) throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");

        return member;
    }
}
