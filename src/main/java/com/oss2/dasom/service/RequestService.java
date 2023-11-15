package com.oss2.dasom.service;

import com.oss2.dasom.domain.*;
import com.oss2.dasom.dto.CreateRequestRequest;
import com.oss2.dasom.dto.UpdateRequestRequest;
import com.oss2.dasom.repository.PostRepository;
import com.oss2.dasom.repository.RequestRepository;
import com.oss2.dasom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Request> getPostId(NanoId postId) {
        Post post = postRepository.findByPostId(postId);
        List<Request> requests = requestRepository.findByPost(post);
        return requests;
    }

    // 사용자별 신청 조회
    public List<Request> getUserId(NanoId userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));;
        List<Request> requests = requestRepository.findByUser(user);
        return requests;
    }

    // 신청 등록
    public NanoId createRequest(CreateRequestRequest dto) {
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않은 회원입니다."));;
        Post post = postRepository.findByPostId(dto.getPostId());
        Request request = Request.builder().title(dto.getTitle())
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
        request.setTitle(dto.getTitle());
        request.setContent(dto.getContent());
        requestRepository.save(request);
    }

    // 신청 삭제
    public void deleteRequest(NanoId requestId) {
        Request request = requestRepository.findByRequestId(requestId);
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
