package com.godcoder.myrest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id //pk 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 1 증가 설정 (시퀀스를 따로 안 만들어도 됨)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //cascade 데이터를 삭제하면 하위 데이터도 모두 삭제 / orphanRemoval 부모가 없는 데이터는 지운다
    @OneToMany(mappedBy = "user")

    private List<Board> boards = new ArrayList<>();



    /* GET SET method */
    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
