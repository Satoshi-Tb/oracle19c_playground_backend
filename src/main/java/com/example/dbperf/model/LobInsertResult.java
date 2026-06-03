package com.example.dbperf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LobInsertResult {

    private Long headerId;
    private Long detailId;
    private Long textLen;
    private Long fragmentCount;
}
