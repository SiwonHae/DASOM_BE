package com.oss2.dasom.controller;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Number;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.dto.*;
import com.oss2.dasom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> findAllPosts(Pageable pageable) {
        Page<PageResponse> postPage = postService.getAllPosts(pageable);
            return ResponseEntity.ok().body(postPage);
    }

    // 특정 사용자가 작성한 게시물 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> findPostByUser(@PathVariable String userId, Pageable pageable) {
        Page<PageResponse> postPage = postService.getPostUserId(userId, pageable);
        return ResponseEntity.ok().body(postPage);
    }

    // 성별 필터 조회
    @GetMapping("/gender/{gender}")
    public ResponseEntity<?> findPostByGender(@PathVariable Gender gender, Pageable pageable) {
        Page<PageResponse> postPage = postService.getPostGender(gender, pageable);
        return ResponseEntity.ok().body(postPage);
    }

    // 인원 필터 조회
    @GetMapping("/number/{number}")
    public ResponseEntity<?> findPostByNumber(@PathVariable Number number, Pageable pageable) {
        Page<PageResponse> postPage = postService.getPostNumber(number, pageable);
        return ResponseEntity.ok().body(postPage);
    }

    // 성별 필터 조회
    @GetMapping("/filter")
    public ResponseEntity<?> findPostByGenderAndNumber(@RequestParam Gender gender,
                                                       @RequestParam Number number,
                                                       Pageable pageable) {
        Page<PageResponse> postPage = postService.getPostGenderAndNumber(gender, number, pageable);
        return ResponseEntity.ok().body(postPage);
    }

    // 게시물 상세 조회
    @GetMapping("/detail/{postId}")
    public ResponseEntity<GetPostResponse> findPost(@PathVariable String postId) {
        GetPostResponse postResponse = new GetPostResponse(postService.getPostId(postId));
        return ResponseEntity.ok()
                .body(postResponse);
    }

    // 게시물 작성
    @PostMapping
    public ResponseEntity createPost(@RequestBody CreatePostRequest request) {
        NanoId postId = postService.createPost(request);
        return new ResponseEntity<>(postId, HttpStatus.CREATED);
    }

    // 게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable String postId, @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok().build();
    }

    // 게시물 삭제
    @PostMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId, @RequestBody UserIdRequest userIdRequest) {
        postService.deletePost(postId, userIdRequest);
        return ResponseEntity.ok().build();
    }


}
