package com.oss2.dasom.controller;

import com.oss2.dasom.domain.NanoId;
import com.oss2.dasom.domain.Request;
import com.oss2.dasom.domain.Result;
import com.oss2.dasom.dto.CreateRequestRequest;
import com.oss2.dasom.dto.GetRequestResponse;
import com.oss2.dasom.dto.UpdatePostRequest;
import com.oss2.dasom.dto.UpdateRequestRequest;
import com.oss2.dasom.service.RequestService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    // postId별 신청 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<GetRequestResponse>> findRequestByPost(@PathVariable Long postId) {
        List<GetRequestResponse> requests = requestService.getPostId(postId)
                .stream()
                .map(GetRequestResponse::new)
                .toList();
        return ResponseEntity.ok().body(requests);
    }

    // userId별 신청 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetRequestResponse>> findRequestByUser(@PathVariable NanoId userId) {
        List<GetRequestResponse> requests = requestService.getUserId(userId)
                .stream()
                .map(GetRequestResponse::new)
                .toList();
        return ResponseEntity.ok().body(requests);
    }

    // 신청 등록
    @PostMapping
    public ResponseEntity createRequest(@RequestBody CreateRequestRequest request) {
        Long requestId = requestService.createRequest(request);
        return new ResponseEntity<>(requestId, HttpStatus.CREATED);
    }

    // 신청 수정
    @PutMapping("/{requestId}")
    public ResponseEntity<Void> updateRequest(@PathVariable Long requestId, @RequestBody UpdateRequestRequest request) {
        requestService.updateRequest(requestId, request);
        return ResponseEntity.ok().build();
    }

    // 신청 삭제
    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.ok().build();
    }

    // 신청 수락/거절
    @PutMapping("/result/{requestId}")
    public ResponseEntity<Void> updateRequestResult(@PathVariable Long requestId, @RequestParam Result result) {
        requestService.updateRequestResult(requestId, result);
        return ResponseEntity.ok().build();
    }
}
