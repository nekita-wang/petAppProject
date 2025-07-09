package com.petlife.platform.home.controller;


import com.petlife.platform.breeds.domain.PetBreed;
import com.petlife.platform.common.core.domain.R;
import com.petlife.platform.common.core.page.TableDataInfo;
import com.petlife.platform.home.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private IHomeService iHomeService;

    @GetMapping("/select")
    public R selectUsr()
    {

    return R.ok(iHomeService.selectHomeUser());
    }

    @GetMapping("/petNum")
    public R petNum()
    {

        return R.ok(iHomeService.selectPetClassNum());
    }
}
