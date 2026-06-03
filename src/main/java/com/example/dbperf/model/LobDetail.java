package com.example.dbperf.model;

import lombok.Data;

@Data
public class LobDetail {

    private Long id;
    private Long headerId;
    private Long no;
    private String fragment;
    private Long fragmentLen;
    private String memo;
}
