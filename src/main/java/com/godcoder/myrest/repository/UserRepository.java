package com.godcoder.myrest.repository;


import com.godcoder.myrest.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"boards"}) //조인을 해서 데이터를 가져옴 fetch 무시
    List<User> findAll();

    User findByUsername(String username);
}
