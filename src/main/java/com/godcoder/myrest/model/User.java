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
    @JsonIgnore
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) //cascade 데이터를 삭제하면 하위 데이터도 모두 삭제 / orphanRemoval 부모가 없는 데이터는 지운다
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // user 를 조회할때 board 의 데이터도 함께 가져온다
    private List<Board> boards = new ArrayList<>();
    /*EAGER 함께 가져올 데이터가 하나의 데이터가 증명됨으로 성능상으로 괜찮음
    OneToOne, ManyToOne
    LAZY 사용하지도 않을 데이터가 다 오기 때문에 과부하가 올 수 있음
    OneToMany, ManyToMany*/



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
