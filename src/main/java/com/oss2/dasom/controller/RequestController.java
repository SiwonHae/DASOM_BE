package com.oss2.dasom.controller;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Result;
import com.oss2.dasom.dto.CreateRequestRequest;
import com.oss2.dasom.dto.RequestPageResponse;
import com.oss2.dasom.dto.UserIdRequest;
import com.oss2.dasom.dto.UpdateRequestRequest;
import com.oss2.dasom.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    // postId별 신청 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<?> findRequestByPost(@PathVariable NanoId postId, @RequestBody UserIdRequest userIdRequest, Pageable pageable) {
        Page<RequestPageResponse> requests = requestService.getPostId(postId, NanoId.of(userIdRequest.getUserId()), pageable);
        return ResponseEntity.ok().body(requests);
    }

    // userId별 신청 조회
    @GetMapping("/user")
    public ResponseEntity<?> findRequestByUser(@RequestBody UserIdRequest userIdRequest, Pageable pageable) {
        Page<RequestPageResponse> requests = requestService.getUserId(NanoId.of(userIdRequest.getUserId()), pageable);
        return ResponseEntity.ok().body(requests);
    }

    // 신청 등록
    @PostMapping
    public ResponseEntity createRequest(@RequestBody CreateRequestRequest request) {
        NanoId requestId = requestService.createRequest(request);
        return new ResponseEntity<>(requestId, HttpStatus.CREATED);
    }

    // 신청 수정
    @PutMapping("/{requestId}")
    public ResponseEntity<Void> updateRequest(@PathVariable String requestId, @RequestBody UpdateRequestRequest request) {
        requestService.updateRequest(NanoId.of(requestId), request);
        return ResponseEntity.ok().build();
    }

    // 신청 삭제
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String requestId, @RequestBody UserIdRequest userIdRequest) {
        requestService.deleteRequest(NanoId.of(requestId), NanoId.of(userIdRequest.getUserId()));
        return ResponseEntity.ok().build();
    }

    // 신청 수락/거절
    @PutMapping("/result/{requestId}")
    public ResponseEntity<Void> updateRequestResult(@PathVariable String requestId, @RequestParam Result result, @RequestBody UserIdRequest userIdRequest) {
        requestService.updateRequestResult(NanoId.of(requestId) , result, NanoId.of(userIdRequest.getUserId()));
        return ResponseEntity.ok().build();
    }
}
