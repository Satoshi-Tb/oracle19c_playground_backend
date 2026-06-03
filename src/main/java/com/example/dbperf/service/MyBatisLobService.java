package com.example.dbperf.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dbperf.mapper.LobMapper;
import com.example.dbperf.model.LobDetail;
import com.example.dbperf.model.LobHeader;
import com.example.dbperf.model.LobInsertResult;
import com.example.dbperf.model.LobUpdateResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyBatisLobService {

    private static final int SAMPLE_TEXT_LENGTH = 10000;
    private static final String SAMPLE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private final LobMapper lobMapper;

    @Transactional
    public LobInsertResult createSampleData() {
        log.info("Insert LOB data by MyBatis start: {}", LocalDateTime.now());

        String sampleText = createSampleText("INSERT_SAMPLE:", 0);

        LobHeader header = new LobHeader();
        header.setText(sampleText);
        header.setTextLen((long) sampleText.length());
        header.setFragmentCount(1L);

        lobMapper.insertHeader(header);
        if (header.getId() == null) {
            throw new IllegalStateException("Failed to get generated T_LOB_HEADER.ID");
        }

        LobDetail detail = new LobDetail();
        detail.setHeaderId(header.getId());
        detail.setNo(1L);
        detail.setFragment(sampleText);
        detail.setFragmentLen((long) sampleText.length());
        detail.setMemo("MyBatis sample data");

        lobMapper.insertDetail(detail);

        log.info("Insert LOB data by MyBatis end: {}", LocalDateTime.now());

        return new LobInsertResult(
                header.getId(),
                detail.getId(),
                header.getTextLen(),
                header.getFragmentCount());
    }

    @Transactional
    public LobUpdateResult updateSampleData() {
        log.info("Update LOB data by MyBatis start: {}", LocalDateTime.now());

        LobInsertResult insertResult = createSampleData();
        String updatedHeaderText = createSampleText("UPDATE_SAMPLE:", 40000);
        String updatedDetailText = createSampleText("UPDATE_SAMPLE:", 40000);

        LobHeader header = new LobHeader();
        header.setId(insertResult.getHeaderId());
        header.setText(updatedHeaderText);
        header.setTextLen((long) updatedHeaderText.length());
        header.setFragmentCount(1L);

        LobDetail detail = new LobDetail();
        detail.setId(insertResult.getDetailId());
        detail.setNo(1L);
        detail.setFragment(updatedDetailText);
        detail.setFragmentLen((long) updatedDetailText.length());
        detail.setMemo("MyBatis updated sample data");

        int headerUpdatedCount = lobMapper.updateHeader(header);
        int detailUpdatedCount = lobMapper.updateDetail(detail);

        log.info("Update LOB data by MyBatis end: {}", LocalDateTime.now());

        return new LobUpdateResult(
                header.getId(),
                detail.getId(),
                headerUpdatedCount,
                detailUpdatedCount,
                header.getTextLen(),
                detail.getFragmentLen());
    }

    private String createSampleText(String prefix, int textLength) {
    		if (textLength <= 0) {
    			textLength = SAMPLE_TEXT_LENGTH;
    		}
        StringBuilder sb = new StringBuilder(textLength);
        sb.append(prefix);
        for (int i = 0; i < textLength; i++) {
            sb.append(SAMPLE_CHARS.charAt(i % SAMPLE_CHARS.length()));
        }
        return sb.substring(0, textLength);
    }
}
