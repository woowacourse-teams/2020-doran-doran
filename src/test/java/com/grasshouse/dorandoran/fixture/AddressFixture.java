package com.grasshouse.dorandoran.fixture;

import com.grasshouse.dorandoran.post.domain.Address;

public class AddressFixture {

    public static final Address ADDRESS = Address.builder()
        .depth1("서울특별시")
        .depth2("송파구")
        .depth3("방이동")
        .build();

}
