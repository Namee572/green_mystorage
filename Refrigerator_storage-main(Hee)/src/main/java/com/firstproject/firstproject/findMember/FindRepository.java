package com.firstproject.firstproject.findMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FindRepository extends JpaRepository <FindMember,Long> {
    Optional<FindMember> findByNameAndBirth(String Name, String birth);
    Optional<FindMember> findByBirthAndNameAndEmail(String birth, String name, String email );

}