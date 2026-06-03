package com.example.dbperf.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LobUpdateResult {

    private Long headerId;
    private Long detailId;
    private Integer headerUpdatedCount;
    private Integer detailUpdatedCount;
    private Long textLen;
    private Long fragmentLen;
}
