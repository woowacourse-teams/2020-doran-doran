package com.grasshouse.dorandoran.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.grasshouse.dorandoran.post.domain.Post;
import java.time.LocalDateTime;
import java.util.List;
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
class PostRepositorySupportTest {

    @Autowired
    private PostRepositorySupport postRepositorySupport;

    static Stream<Arguments> generateDays() {
        return Stream.of(
            Arguments.of(14, 1, 4),
            Arguments.of(11, 1, 3),
            Arguments.of(5, 1, 2),
            Arguments.of(2, 1, 1)
        );
    }

    @Sql(value = "/dateTimePost.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/dateTimePostDelete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("특정 시간 사이의 글을 가져온다.")
    @ParameterizedTest
    @MethodSource("generateDays")
    void findPostBetweenDate(int dayBefore, int dayAfter, int expectedPostSize) {
        LocalDateTime pivotDay = LocalDateTime.of(2020, 6, 13, 0, 0, 0);

        List<Post> posts = postRepositorySupport
            .findPostContainsKeywordBetweenDate("", pivotDay.minusDays(dayBefore), pivotDay.plusDays(dayAfter));

        assertThat(posts).hasSize(expectedPostSize);
    }

    @Sql(value = "/dateTimePost.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/dateTimePostDelete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("특정 키워드를 포함한 글을 가져온다.")
    @Test
    void findPostBetween() {
        List<Post> posts = postRepositorySupport
            .findPostContainsKeywordBetweenDate("오늘", null, null);

        assertThat(posts).hasSize(2);
    }
}