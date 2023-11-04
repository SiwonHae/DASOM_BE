package com.oss2.dasom.controller;

import com.oss2.dasom.dto.CreatePostRequest;
import com.oss2.dasom.dto.GetPostResponse;
import com.oss2.dasom.dto.UpdatePostRequest;
import com.oss2.dasom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    @Autowired
    private PostService postService;

    // 모든 게시물 조회
    @GetMapping
    public ResponseEntity<List<GetPostResponse>> findAllPosts() {
        List<GetPostResponse> posts = postService.getAllPosts()
                .stream()// 각 post 객체들을 GetPostResponse로 매핑
                .map(GetPostResponse::new)
                .toList();
        return ResponseEntity.ok()
                .body(posts);
    }

    // 특정 게시물 조회
    @GetMapping("/{id}")
    public ResponseEntity<GetPostResponse> findPost(@PathVariable Long postId) {
        GetPostResponse postResponse = new GetPostResponse(postService.getPostId(postId));
        return ResponseEntity.ok()
                .body(postResponse);
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity createPost(@RequestBody CreatePostRequest request) {
        Long postId = postService.createPost(request);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok().build();
    }

    // postId로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }


}
