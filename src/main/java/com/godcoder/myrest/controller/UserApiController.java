package com.godcoder.myrest.controller;

import com.godcoder.myrest.mapper.UserMapper;
import com.godcoder.myrest.model.Board;
import com.godcoder.myrest.model.QUser;
import com.godcoder.myrest.model.User;
import com.godcoder.myrest.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.godcoder.myrest.model.QUser.user;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

        @Autowired
        private UserRepository repository;

        @Autowired
        private UserMapper userMapper;

        // Aggregate root
        // tag::get-aggregate-root[]
        @GetMapping("/users")
        Iterable<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
            Iterable<User> users =  null; // Iterable = list 부모
            Predicate predicate = null;

            switch (method){
                case "query" :
                    users = repository.findByUsernameQuery(text);
                    break;

                case "nativeQuery" :
                    users = repository.findByUsernameNativeQuery(text);
                    break;

                case "querydsl" :
                    predicate = user.username.contains(text);
                    users = repository.findAll(predicate);
                    break;

                case "querydslCustom" :
                    users = repository.findByUsernameCustom(text);
                    break;

                case "querydslJdbc" :
                    users = repository.findByUsernameJdbc(text);
                    break;

                case "mybatis" :
                    users = userMapper.getUsers(text);
                    break;

                default:
                    users = repository.findAll();
                    break;
            }
            return users;

        }
        // end::get-aggregate-root[]
//        BooleanExpression booleanExpression = user.username.contains(text);
//                    if (true) {
//                        booleanExpression = booleanExpression.and(user.username.eq("jc"));
//                        users = repository.findAll(booleanExpression);
//                        break;
//                    }

        @PostMapping("/users")
        User newUser(@RequestBody User newUser) {
            return repository.save(newUser);
        }

        // Single item

        @GetMapping("/users/{id}")
        User one(@PathVariable Long id) {
            return repository.findById(id).orElse(null);
        }


        @PutMapping("/users/{id}")
        User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

            return repository.findById(id)
                    .map(user -> {
                        /*user.setTitle(newUser.getTitle());
                        user.setContent(newUser.getContent());
                        user.setBoards(newUser.getBoards());*/
                        user.getBoards().clear();
                        user.getBoards().addAll(newUser.getBoards());
                        for(Board board : user.getBoards()){
                            board.setUser(user);
                        }
                        return repository.save(user);
                    })
                    .orElseGet(() -> {
                        newUser.setId(id);
                        return repository.save(newUser);
                    });
        }

        @DeleteMapping("/users/{id}")
        void deleteUser(@PathVariable Long id) {
            repository.deleteById(id);
        }
}

