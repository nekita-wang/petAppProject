package com.petlife.platform.home.service;

import com.petlife.platform.home.domain.HomeUserDto;

public interface IHomeService {

    HomeUserDto selectHomeUser();

    String selectPetClassNum();
}
