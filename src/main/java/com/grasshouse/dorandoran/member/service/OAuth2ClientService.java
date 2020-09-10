package com.grasshouse.dorandoran.member.service;

import com.grasshouse.dorandoran.common.exception.UnsupportedOperationException;
import com.grasshouse.dorandoran.member.domain.Member;
import com.grasshouse.dorandoran.member.repository.MemberRepository;
import java.util.LinkedHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2ClientService implements OAuth2AuthorizedClientService {

    private final MemberRepository memberRepository;

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient oAuth2AuthorizedClient,
        Authentication authentication) {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String id = String.valueOf(oauth2User.getAttributes().get("id"));
        if (!memberRepository.existsMemberByoAuthId(id)) {
            String nickname = (String)
                ((LinkedHashMap)
                    ((LinkedHashMap) oauth2User.getAttribute("kakao_account"))
                        .get("profile"))
                    .get("nickname");

            Member authorizedMember = Member.builder()
                .nickname(nickname)
                .oAuthId(id)
                .build();
            memberRepository.save(authorizedMember);
        }
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId,
        String principalName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        throw new UnsupportedOperationException();
    }
}
