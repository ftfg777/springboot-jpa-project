package com.godcoder.myrest.repository;

import com.godcoder.myrest.model.QUser;
import com.godcoder.myrest.model.User;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class CustomizedUserRepositoryImpl implements CustomizedUserRepository{
    @PersistenceContext
    private EntityManager em;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> findByUsernameCustom(String username) {
        QUser qUser = QUser.user;
        JPAQuery<?> query = new JPAQuery<>(em);
        return query.select(qUser)
                .from(qUser)
                .where(qUser.username.contains(username))
                .fetch();
    }

    @Override
    public List<User> findByUsernameJdbc(String username) {
        String sql = "SELECT * FROM USER WHERE username like ?";
        String likeStr = "%" + username + "%";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper(User.class), likeStr);
    }
}
