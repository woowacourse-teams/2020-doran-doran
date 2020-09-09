package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.post.dto.PostResponse;
import com.grasshouse.dorandoran.post.service.PostFilterService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

class PostFilterControllerTest extends CommonControllerTest {

    @MockBean
    private PostFilterService PostFilterService;

    @DisplayName("키워드와 날짜로 검색된 글을 반환한다.")
    @Test
    void getSearchResultsPosts() throws Exception {
        String url = "/posts/filter";
        when(PostFilterService.showSearchResults(any())).thenReturn(postResponses());

        this.mockMvc.perform(get(url)
            .queryParam("keyword", "내용")
            .queryParam("startDate", "2020-05-05 00:00:00")
            .queryParam("endDate", "2020-08-08 23:59:59")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print());

        verify(PostFilterService).showSearchResults(any());
    }

    @DisplayName("날짜 필터링이 없는 경우")
    @Test
    void getSearchResultsPosts2() throws Exception {
        String url = "/posts/filter";
        when(PostFilterService.showSearchResults(any())).thenReturn(postResponses());

        this.mockMvc.perform(get(url)
            .queryParam("keyword", "내용")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print()).andReturn();

        verify(PostFilterService).showSearchResults(any());
    }

    @DisplayName("[예외] 시작 날짜가 마감 날짜보다 뒤에 있는 경우")
    @Test
    void getSearchResultsPosts3() throws Exception {
        String url = "/posts/filter";
        when(PostFilterService.showSearchResults(any())).thenReturn(postResponses());

        this.mockMvc.perform(get(url)
            .queryParam("keyword", "내용")
            .queryParam("startDate", "2021-05-05 00:00:00")
            .queryParam("endDate", "2020-08-08 23:59:59")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @DisplayName("[예외] 날짜가 올바른 형식이 아닌 경우")
    @Test
    void getSearchResultsPosts4() throws Exception {
        String url = "/posts/filter";
        when(PostFilterService.showSearchResults(any())).thenReturn(postResponses());

        this.mockMvc.perform(get(url)
            .queryParam("keyword", "내용")
            .queryParam("startDate", "2021-05-05 00:00:00")
            .queryParam("endDate", "마감날짜")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    private List<PostResponse> postResponses() {
        return Arrays.asList(PostResponse.from(PERSIST_POST));
    }
}
