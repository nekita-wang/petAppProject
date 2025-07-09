package com.petlife.platform.home.service.impl;

import com.petlife.platform.home.domain.HomeUserDto;
import com.petlife.platform.home.mapper.HomeMapper;
import com.petlife.platform.home.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements IHomeService {

    @Autowired
    private HomeMapper homeMapper;

    @Override
    public HomeUserDto selectHomeUser() {

        HomeUserDto userDto=new HomeUserDto();
        userDto.setTotalUsers(homeMapper.selectUserNum());
        userDto.setLastLoginUser(homeMapper.getLastLoginUser());
        userDto.setPetBreedsCount(homeMapper.selectPetBreed());
        return userDto;
    }

    @Override
    public String selectPetClassNum() {
        return homeMapper.selectPetClassNum();
    }
}
