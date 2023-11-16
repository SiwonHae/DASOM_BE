package com.oss2.dasom.service;

import com.oss2.dasom.domain.*;
import com.oss2.dasom.dto.CreateRequestRequest;
import com.oss2.dasom.dto.RequestPageResponse;
import com.oss2.dasom.dto.UpdateRequestRequest;
import com.oss2.dasom.repository.PostRepository;
import com.oss2.dasom.repository.RequestRepository;
import com.oss2.dasom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
    public Page<RequestPageResponse> getPostId(NanoId postId, Pageable pageable) {
        Post post = postRepository.findByPostId(postId);
        Page<Request> requestPage = requestRepository.findByPost(post, pageable);
        return requestPage.map(request -> RequestPageResponse.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdDate(request.getCreatedDate())
                .result(request.getResult())
                .nickname(request.getUser().getNickname())
                .requestId(request.getRequestId())
                .build()
        );
    }

    // 사용자별 신청 조회
    public Page<RequestPageResponse> getUserId(NanoId userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));;
        Page<Request> requestPage = requestRepository.findByUser(user, pageable);
        return requestPage.map(request -> RequestPageResponse.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .createdDate(request.getCreatedDate())
                .result(request.getResult())
                .nickname(request.getUser().getNickname())
                .requestId(request.getRequestId())
                .build()
        );
    }

    // 신청 등록
    public NanoId createRequest(CreateRequestRequest dto) {
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));;
        Post post = postRepository.findByPostId(dto.getPostId());
        Request request = Request.builder()
                .requestId(NanoId.makeId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .post(post)
                .build();
        requestRepository.save(request);

        notificationService.saveRequest(request, NotificationKind.REQUEST);

        return request.getRequestId();
    }

    // 신청 수정 (제목, 내용만 수정 가능)
    public void updateRequest(NanoId requestId, UpdateRequestRequest dto) {
        Request request = requestRepository.findByRequestId(requestId);

        if (!dto.getUserId().toString().equals(request.getUser().getUserId().toString())) {
            throw new IllegalArgumentException("신청자만 수정/삭제 가능합니다.");
        }

        request.setTitle(dto.getTitle());
        request.setContent(dto.getContent());
        requestRepository.save(request);
    }

    // 신청 삭제
    public void deleteRequest(NanoId requestId, NanoId userId) {
        Request request = requestRepository.findByRequestId(requestId);
        if (!userId.toString().equals(request.getUser().getUserId().toString())) {
            throw new IllegalArgumentException("신청자만 수정/삭제 가능합니다.");
        }
        requestRepository.delete(request);
    }

    // 신청 결과 설정
    public void updateRequestResult(NanoId requestId, Result result) {
        Request request = requestRepository.findByRequestId(requestId);
        request.setResult(result);
        requestRepository.save(request);
        if(request.getResult() == Result.YES) {
            notificationService.saveRequest(request, NotificationKind.YES);
        }
        else {
            notificationService.saveRequest(request, NotificationKind.NO);
        }
    }
}
