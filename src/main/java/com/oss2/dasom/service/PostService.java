package com.oss2.dasom.service;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.CreatePostRequest;
import com.oss2.dasom.dto.UpdatePostRequest;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.repository.PostRepository;
import com.oss2.dasom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    // 모든 게시물 조회
    public Page<Post> getAllPosts(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest);
    }

    // 게시물 상세조회
    public Post getPostId(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    // 모집 게시물 등록
    public NanoId createPost(CreatePostRequest dto) {
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));
        ;

        Post post = Post.builder().title(dto.getTitle())
                .content(dto.getContent())
                .openKakaoAddress(dto.getOpenKakaoAddress())
                .alcohol(dto.getAlcohol())
                .user(user)
                .number(dto.getNumber())
                .gender(dto.getGender()).build();
        postRepository.save(post);
        return post.getPostId();
    }

    // 게시물 수정
    public void updatePost(Long postId, UpdatePostRequest dto) {
        Post post = postRepository.findById(postId).orElse(null);
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setOpenKakaoAddress(dto.getOpenKakaoAddress());
        post.setAlcohol(dto.getAlcohol());
        post.setNumber(dto.getNumber());
        post.setGender(dto.getGender());
        postRepository.save(post);
    }

    // 게시물 삭제
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        postRepository.delete(post);
        return;
    }

}
