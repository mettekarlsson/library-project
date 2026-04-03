package library.services;

import library.models.Member;
import library.repositories.MemberRepository;

import java.util.ArrayList;

public class MemberService {
    MemberRepository memberRepository = new MemberRepository();

    public ArrayList<Member> getAllMembers() {
       return memberRepository.getAllMembers();
    }

    public Member getMemberInfoById(int id) {
        return memberRepository.getMemberInfoById(id);
    }

}
