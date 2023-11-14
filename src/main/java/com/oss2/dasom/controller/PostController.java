package com.oss2.dasom.controller;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.dto.CreatePostRequest;
import com.oss2.dasom.dto.GetPostResponse;
import com.oss2.dasom.dto.UpdatePostRequest;
import com.oss2.dasom.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<List<GetPostResponse>> findAllPosts(@RequestParam(defaultValue = "1") int page) {
        try {
            PageRequest pageRequest = PageRequest.of(page - 1, 10);
            Page<Post> postPage = postService.getAllPosts(pageRequest);
            List<GetPostResponse> posts = postPage.getContent()
                    .stream()
                    .map(GetPostResponse::new)
                    .toList();
            return ResponseEntity.ok().body(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }

    }

    // 게시물 상세 조회
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> findPost(@PathVariable Long postId) {
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
    public ResponseEntity<Void> updatePost(@PathVariable Long postId, @RequestBody UpdatePostRequest request) {
        postService.updatePost(postId, request);
        return ResponseEntity.ok().build();
    }

    // postId로 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }


}
