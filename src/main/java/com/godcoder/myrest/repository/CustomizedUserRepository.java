package com.godcoder.myrest.repository;

import com.godcoder.myrest.model.User;

import java.util.List;

public interface CustomizedUserRepository {

    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);

}
