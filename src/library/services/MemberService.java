package library.services;

import library.models.Member;
import library.repositories.MemberRepository;

import java.util.ArrayList;

public class MemberService {
    MemberRepository memberRepository = new MemberRepository();

    public ArrayList<Member> getAllMembers() {
       return memberRepository.getAllMembers();
    }

    public Member getMemberById(int id) {
        return memberRepository.getMemberById(id);
    }

    public void updateMemberInfo(String column, String newValue) {
        memberRepository.updateMemberInfo(column, newValue);
    }

}
