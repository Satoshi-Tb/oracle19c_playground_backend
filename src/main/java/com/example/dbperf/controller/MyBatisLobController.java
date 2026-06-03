package com.example.dbperf.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dbperf.model.LobInsertResult;
import com.example.dbperf.model.LobUpdateResult;
import com.example.dbperf.service.MyBatisLobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mybatis")
@RequiredArgsConstructor
public class MyBatisLobController {

    private final MyBatisLobService service;

    @PostMapping("/lob")
    public LobInsertResult insertLobData() {
        return service.createSampleData();
    }

    @PostMapping("/lob/update")
    public LobUpdateResult updateLobData() {
        return service.updateSampleData();
    }
}
