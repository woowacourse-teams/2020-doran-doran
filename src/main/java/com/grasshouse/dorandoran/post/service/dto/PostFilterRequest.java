package com.grasshouse.dorandoran.post.service.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostFilterRequest {

    private String keyword;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
