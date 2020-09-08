package com.grasshouse.dorandoran.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.post.dto.PostFilterRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
class PostFilterServiceTest {

    @Autowired
    private PostFilterService PostFilterService;

    static Stream<Arguments> generateDays() {
        return Stream.of(
            Arguments.of(toDate("2020-05-31 00:00:00"), toDate("2020-06-30 23:59:59"), 4),
            Arguments.of(toDate("2020-06-02 00:00:00"), toDate("2020-06-30 23:59:59"), 3),
            Arguments.of(toDate("2020-06-04 00:00:00"), toDate("2020-06-30 23:59:59"), 2),
            Arguments.of(toDate("2020-06-11 00:00:00"), toDate("2020-06-30 23:59:59"), 1)
        );
    }

    @Sql(value = "/dateTimePost.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/dateTimePostDelete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("키워드로 검색된 글들을 반환한다.")
    @Test
    void showSearchResultsByKeyword() {
        PostFilterRequest request = new PostFilterRequest("오늘", null, null);

        assertThat(PostFilterService.showSearchResults(request)).hasSize(2);
    }

    @Sql(value = "/dateTimePost.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/dateTimePostDelete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("특정 시간 사이의 글들을 반환한다.")
    @ParameterizedTest
    @MethodSource("generateDays")
    void showSearchResultsByDate(LocalDateTime startDate, LocalDateTime endDate, int searchResult) {
        PostFilterRequest request = new PostFilterRequest(null, startDate, endDate);
        assertThat(PostFilterService.showSearchResults(request))
            .hasSize(searchResult);
    }

    private static LocalDateTime toDate(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
