package com.example.dbperf.model;

import lombok.Data;

@Data
public class LobHeader {

    private Long id;
    private String text;
    private Long textLen;
    private Long fragmentCount;
}
