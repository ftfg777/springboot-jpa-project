package com.godcoder.myrest.repository;


import com.godcoder.myrest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
