package com.oss2.dasom.dto;

import com.oss2.dasom.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostList {
    private List<GetPostResponse> postList = new ArrayList<>();

    private Long totalPages;
    private Long totalCount;

    @Builder
    public PostList(List<Post> postList, Long totalPages, Long totalCount) {
        this.postList = postList.stream()
                .map(GetPostResponse::new).collect(Collectors.toList());
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }
}
