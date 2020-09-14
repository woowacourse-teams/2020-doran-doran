package com.grasshouse.dorandoran.fixture;

import com.grasshouse.dorandoran.post.domain.Address;

public class AddressFixture {

    public static final Address ADDRESS = Address.builder()
        .depth1("서울특별시")
        .depth2("송파구")
        .depth3("방이동")
        .build();

    public static final Address AUTHOR_ADDRESS = Address.builder()
        .depth1("부산광역시")
        .depth2("연제구")
        .depth3("연산동")
        .build();
}
