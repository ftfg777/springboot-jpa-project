package com.godcoder.myrest.repository;

import com.godcoder.myrest.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<Board, Long> {//테이블의 model 클래스, pk가 되는 자료형

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
}
