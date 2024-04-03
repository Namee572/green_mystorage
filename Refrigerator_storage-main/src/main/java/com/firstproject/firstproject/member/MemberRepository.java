package com.firstproject.firstproject.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickName(String nickName);
    //아이디 비밀번호 찾기 Repository
    Optional<Member> findByNameAndBirth(String Name, String birth);
    Optional<Member> findByBirthAndNameAndEmail(String birth, String name, String email );

    Optional<Member> findByPassword(String password);
}

// 준재님코드
//@Repository
//public interface MemberRepository extends JpaRepository<Member, Long> {
//
//    Member findByEmailContainingAndUsernameContainingAndNumberContaining(String email, String username, String number);
//}