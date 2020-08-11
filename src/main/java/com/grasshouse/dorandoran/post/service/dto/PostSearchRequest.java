package com.grasshouse.dorandoran.post.service.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchRequest {

    private String keyword;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
