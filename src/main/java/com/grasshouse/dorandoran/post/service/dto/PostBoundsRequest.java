package com.grasshouse.dorandoran.post.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostBoundsRequest {

    Double upperBound;
    Double lowerBound;
    Double leftBound;
    Double rightBound;
}
