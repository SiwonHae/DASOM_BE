package com.oss2.dasom.service;

import com.oss2.dasom.domain.Gender;
import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Post;
import com.oss2.dasom.domain.User;
import com.oss2.dasom.dto.CreatePostRequest;
import com.oss2.dasom.dto.GetPostResponse;
import com.oss2.dasom.dto.PageResponse;
import com.oss2.dasom.dto.UpdatePostRequest;
import com.oss2.dasom.repository.PostRepository;
import com.oss2.dasom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    // 모든 게시물 조회
    public Page<PageResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        return postPage.map(post-> PageResponse.builder()
                .title(post.getTitle())
                .createdDate(post.getCreatedDate())
                .nickname(post.getUser().getNickname())
                .number(post.getNumber())
                .gender(post.getGender())
                .postId(post.getPostId())
                .build()
        );
    }

    // 게시물 상세조회
    public Post getPostId(String postId) {
        return postRepository.findByPostId(NanoId.of(postId));
    }

    // 특정 사용자가 작성한 게시물 조회
    public Page<PageResponse> getPostUserId(String userId, Pageable pageable) {
        User user = userRepository.findByUserId(NanoId.of(userId)).orElseThrow(()->
                new IllegalArgumentException("존재하지 않은 회원입니다."));
        Page<Post> postPage = postRepository.findByUser(user, pageable).get();
        return postPage.map(post-> PageResponse.builder()
                .title(post.getTitle())
                .createdDate(post.getCreatedDate())
                .nickname(post.getUser().getNickname())
                .number(post.getNumber())
                .gender(post.getGender())
                .postId(post.getPostId())
                .build()
        );
    }

    // 모집 성별 필터 조회
    public Page<PageResponse> getPostGender(Gender gender, Pageable pageable) {
        Page<Post> postPage = postRepository.findByGender(gender, pageable).orElse(null);
        return postPage.map(post-> PageResponse.builder()
                .title(post.getTitle())
                .createdDate(post.getCreatedDate())
                .nickname(post.getUser().getNickname())
                .number(post.getNumber())
                .gender(post.getGender())
                .postId(post.getPostId())
                .build()
        );
    }

    // 모집 게시물 등록
    @Transactional
    public NanoId createPost(CreatePostRequest dto) {
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        Post post = Post.builder()
                .postId(NanoId.makeId())
                .title(dto.getTitle())
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
    public void updatePost(String postId, UpdatePostRequest dto) {

        Post post = postRepository.findByPostId(NanoId.of(postId));
        if (!dto.getUserId().toString().equals(post.getUser().getUserId().toString())) {
            throw new IllegalArgumentException("작성자만 수정/삭제 가능합니다.");
        }
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setOpenKakaoAddress(dto.getOpenKakaoAddress());
        post.setAlcohol(dto.getAlcohol());
        post.setNumber(dto.getNumber());
        post.setGender(dto.getGender());
        postRepository.save(post);
    }

    // 게시물 삭제
    public void deletePost(String postId, NanoId userId) {
        Post post = postRepository.findByPostId(NanoId.of(postId));
        if (!userId.toString().equals(post.getUser().getUserId().toString())) {
            throw new IllegalArgumentException("작성자만 수정/삭제 가능합니다.");
        }
        postRepository.delete(post);
    }

}
