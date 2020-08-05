package com.grasshouse.dorandoran.post.controller;

import static com.grasshouse.dorandoran.fixture.PostFixture.PERSIST_POST;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.grasshouse.dorandoran.common.CommonControllerTest;
import com.grasshouse.dorandoran.post.service.PostSearchService;
import com.grasshouse.dorandoran.post.service.dto.PostResponse;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

class PostSearchControllerTest extends CommonControllerTest {

    @MockBean
    private PostSearchService postSearchService;

    @DisplayName("키워드로 검색된 글을 반환한다.")
    @Test
    void getSearchResultsPosts() throws Exception {
        String url = "/posts/search?keyword=내용";

        when(postSearchService.showSearchResults(anyString())).thenReturn(postResponses());

        this.mockMvc.perform(get(url)
            .accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(print());

        verify(postSearchService).showSearchResults(anyString());
    }

    private List<PostResponse> postResponses() {
        return Arrays.asList(PostResponse.from(PERSIST_POST));
    }
}