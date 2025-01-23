package com.circleaf.circleaf_api.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.circleaf.circleaf_api.mapper.MemberMapper;
import com.circleaf.circleaf_api.model.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    
    private final MemberMapper memberMapper;

    public List<Member> findAll() {
        return memberMapper.findAll();
    }

    public Member get(Long id) {
        return memberMapper.get(id);
    }

    public int insert(Member member) {
        return memberMapper.insert(member);
    }

    public int update(Member member) {
        return memberMapper.update(member);
    }

    public int delete(Long id) {
        return memberMapper.delete(id);
    }

    /* 限定取得API */

    public List<Member> getJoinMembers(Long id){
        return memberMapper.getJoinMembers(id);
    }
}
