package com.godcoder.myrest.controller;


import com.godcoder.myrest.model.Board;
import com.godcoder.myrest.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
class BoardApiController {

        private final BoardRepository repository;

        // Aggregate root
        // tag::get-aggregate-root[]
        // 글 목록 보기
        @GetMapping("/boards")
        List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                        @RequestParam(required = false, defaultValue = "") String content) {
            if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
                return repository.findAll();
            }else{
                return repository.findByTitleOrContent(title, content);
            }

        }
        // end::get-aggregate-root[]

        @PostMapping("/boards")
        Board newBoard(@RequestBody Board newBoard) {
            return repository.save(newBoard);
        }

        // Single item

        @GetMapping("/boards/{id}")
        Board one(@PathVariable Long id) {
            return repository.findById(id).orElse(null);
        }


        @PutMapping("/boards/{id}")
        Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

            return repository.findById(id)
                    .map(board -> { // 해당 ID값 게시글이 있는 경우 수정으로 처리
                        board.setTitle(newBoard.getTitle());
                        board.setContent(newBoard.getContent());
                        return repository.save(board);
                    })
                    .orElseGet(() -> { // 해당 ID값 게시글이 없는 경우 새글로 처리 (orElseGet == null일 경우에 발동)
                        newBoard.setId(id);
                        return repository.save(newBoard);
                    });
        }

        @Secured("ROLE_ADMIN")
        @DeleteMapping("/boards/{id}")
        void deleteBoard(@PathVariable Long id) {
            repository.deleteById(id);
        }
}

