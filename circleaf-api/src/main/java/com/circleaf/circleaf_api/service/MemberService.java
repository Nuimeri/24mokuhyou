package com.circleaf.circleaf_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.circleaf.circleaf_api.model.Member;
import com.circleaf.circleaf_api.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<Member> findAll(){
        return memberRepository.findAll();
    }

    @Transactional(readOnly = false)
    public int insert(Member member){
        return memberRepository.insert(member);
    }

    @Transactional(readOnly = true)
    public Member get(Long id){
        return memberRepository.get(id);
    }

    @Transactional(readOnly = false)
    public int update(Member member){
        return memberRepository.update(member);
    }

    @Transactional(readOnly = true)
    public int delete(Long id){
        return memberRepository.delete(id);
    }

    /* 限定取得API */

    @Transactional(readOnly = true)
    public List<Member> getJoinMembers(Long id){
        return memberRepository.getJoinMembers(id);
    }
}
