package com.oss2.dasom.service;

import com.oss2.dasom.domain.*;
import com.oss2.dasom.dto.CreateRequestRequest;
import com.oss2.dasom.dto.RequestPageResponse;
import com.oss2.dasom.dto.UpdateRequestRequest;
import com.oss2.dasom.dto.UserIdRequest;
import com.oss2.dasom.repository.PostRepository;
import com.oss2.dasom.repository.RequestRepository;
import com.oss2.dasom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    // 게시물별 신청 조회
    public Page<RequestPageResponse> getPostId(String postId, Pageable pageable) {
        Post post = postRepository.findByPostId(NanoId.of(postId));
        if (post == null) {
            throw new IllegalArgumentException("존재하지 않는 글입니다.");
        }

        User user = userRepository.findByUserIdAndActiveTrue(post.getUser().getUserId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        Page<Request> requestPage = requestRepository.findByPost(post, pageable);
        return requestPage.map(request -> RequestPageResponse.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdDate(request.getCreatedDate())
                .result(request.getResult())
                .nickname(request.getUser().getNickname())
                .requestId(request.getRequestId())
                .userId(request.getUser().getUserId())
                .postId(request.getPost().getPostId())
                .build()
        );
    }

    // 사용자별 신청 조회
    public Page<RequestPageResponse> getUserId(String userId, Pageable pageable) {

        User user = userRepository.findByUserIdAndActiveTrue(NanoId.of(userId)).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        Page<Request> requestPage = requestRepository.findByUser(user, pageable);
        return requestPage.map(request -> RequestPageResponse.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdDate(request.getCreatedDate())
                .result(request.getResult())
                .nickname(request.getUser().getNickname())
                .requestId(request.getRequestId())
                .userId(request.getUser().getUserId())
                .postId(request.getPost().getPostId())
                .build()
        );
    }

    // 신청 등록
    public NanoId createRequest(CreateRequestRequest dto) {

        User user = userRepository.findByUserIdAndActiveTrue(dto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));

        Post post = postRepository.findByPostId(dto.getPostId());
        if (post == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }

        List<Request> requestList = requestRepository.findByPost(post);

        // 자기 자신의 글에는 신청 불가
        if (requestList.isEmpty()) {
            Post dtoPost = postRepository.findByPostId(dto.getPostId());

            if (dto.getUserId().equals(dtoPost.getUser().getUserId())) {
                throw new IllegalArgumentException("본인의 글에는 신청을 할 수 없습니다.");
            }
        }

        for (Request request : requestList) {
            // 자기 자신의 글에는 신청 불가
            if (request.getPost().getUser().getUserId().equals(dto.getUserId())) {
                throw new IllegalArgumentException("본인의 글에는 신청을 할 수 없습니다.");
            }

            // 이미 수락한 신청이 있다면 신청 불가
            if (request.getResult() == Result.YES) {
                throw new IllegalArgumentException("이미 마감된 모집 게시물입니다.");
            }
            // 이미 신청한 게시물은 신청 불가
            if (request.getUser().getUserId().toString().equals(user.getUserId().toString())) {
                throw new IllegalArgumentException("이미 신청한 내역이 있습니다.");
            }
        }

        Request request = Request.builder()
                .requestId(NanoId.makeId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .post(post)
                .build();

        requestRepository.save(request);

        // 신청했을 때에는 POST 작성자에게 알림 생성
        notificationService.saveRequest(request, post.getUser().getUserId() ,NotificationKind.REQUEST);

        return request.getRequestId();
    }

    // 신청 수정 (제목, 내용만 수정 가능)
    @Transactional
    public void updateRequest(NanoId requestId, UpdateRequestRequest dto) {
        Request request = requestRepository.findByRequestId(requestId);
        if (request == null) {
            throw new IllegalArgumentException("존재하지 않는 신청입니다.");
        }

        if (!dto.getUserId().toString().equals(request.getUser().getUserId().toString())) {
            throw new IllegalArgumentException("신청자만 수정/삭제 가능합니다.");
        }

        request.setTitle(dto.getTitle());
        request.setContent(dto.getContent());
        requestRepository.save(request);
    }

    // 신청 삭제
    public void deleteRequest(String requestId, UserIdRequest userIdRequest) {
        Request request = requestRepository.findByRequestId(NanoId.of(requestId));

        if (request.getResult() == Result.YES) {
            throw new IllegalArgumentException("이미 수락된 신청은 삭제할 수 없습니다.");
        }

        if (!userIdRequest.getUserId().equals(request.getUser().getUserId().toString())) {
            throw new IllegalArgumentException("신청자만 수정/삭제 가능합니다.");
        }

        requestRepository.delete(request);
    }

    // 신청 결과 설정
    @Transactional
    public void updateRequestResult(NanoId requestId, Result result, NanoId userId) {
        Request request = requestRepository.findByRequestId(requestId);

        Post post = postRepository.findByPostId(request.getPost().getPostId());
        System.out.println(post.getUser().getUserId());
        System.out.println(userId.toString());

        if (!post.getUser().getUserId().toString().equals(userId.toString())) {

            throw new IllegalArgumentException("게시물 작성자만 수락/거절 할 수 있습니다.");
        }

        if (!post.isActive()) {
            throw new IllegalArgumentException("이미 수락한 신청이 있습니다.");
        }

        request.setResult(result);
        requestRepository.save(request);

        if (request.getResult() == Result.YES) {
            notificationService.saveRequest(request, request.getUser().getUserId() ,NotificationKind.YES);

            // 마감 처리
            post.setActive(false);
        } else { // 거절 일 때
            notificationService.saveRequest(request, request.getUser().getUserId(), NotificationKind.NO);
        }
    }
}
