package com.grasshouse.dorandoran.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostBoundsRequest {

    Double upperBound;
    Double lowerBound;
    Double leftBound;
    Double rightBound;
}
