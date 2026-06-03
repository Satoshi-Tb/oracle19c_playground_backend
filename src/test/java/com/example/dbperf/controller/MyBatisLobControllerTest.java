package com.example.dbperf.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.dbperf.model.LobInsertResult;
import com.example.dbperf.model.LobUpdateResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MyBatisLobControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final List<Long> createdHeaderIds = new ArrayList<>();
    private final List<Long> createdDetailIds = new ArrayList<>();

    @AfterEach
    void tearDown() {
        createdDetailIds.forEach(id -> jdbcTemplate.update("DELETE FROM T_LOB_DETAIL WHERE ID = ?", id));
        createdHeaderIds.forEach(id -> jdbcTemplate.update("DELETE FROM T_LOB_HEADER WHERE ID = ?", id));
    }

    @Test
    @DisplayName("LOBデータ登録APIがCLOBを登録し、DBに同じ長さで保存されること")
    void insertLobData() {
        ResponseEntity<LobInsertResult> response =
                restTemplate.postForEntity("/mybatis/lob", null, LobInsertResult.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        LobInsertResult body = response.getBody();
        createdHeaderIds.add(body.getHeaderId());
        createdDetailIds.add(body.getDetailId());

        assertThat(body.getHeaderId()).isNotNull();
        assertThat(body.getDetailId()).isNotNull();
        assertThat(body.getFragmentCount()).isEqualTo(1L);

        Long headerTextLen = queryLong("""
                SELECT DBMS_LOB.GETLENGTH(TEXT)
                FROM T_LOB_HEADER
                WHERE ID = ?
                """, body.getHeaderId());
        Long detailFragmentLen = queryLong("""
                SELECT DBMS_LOB.GETLENGTH(FRAGMENT)
                FROM T_LOB_DETAIL
                WHERE ID = ?
                  AND HEADER_ID = ?
                """, body.getDetailId(), body.getHeaderId());

        assertThat(headerTextLen).isEqualTo(body.getTextLen());
        assertThat(detailFragmentLen).isEqualTo(body.getTextLen());
    }

    @Test
    @DisplayName("LOBデータ更新APIがCLOBと明細メモを更新すること")
    void updateLobData() {
        ResponseEntity<LobUpdateResult> response =
                restTemplate.postForEntity("/mybatis/lob/update", null, LobUpdateResult.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        LobUpdateResult body = response.getBody();
        createdHeaderIds.add(body.getHeaderId());
        createdDetailIds.add(body.getDetailId());

        assertThat(body.getHeaderId()).isNotNull();
        assertThat(body.getDetailId()).isNotNull();
        assertThat(body.getHeaderUpdatedCount()).isEqualTo(1);
        assertThat(body.getDetailUpdatedCount()).isEqualTo(1);
        assertThat(body.getTextLen()).isEqualTo(40000L);
        assertThat(body.getFragmentLen()).isEqualTo(40000L);

        String headerPrefix = jdbcTemplate.queryForObject("""
                SELECT DBMS_LOB.SUBSTR(TEXT, 14, 1)
                FROM T_LOB_HEADER
                WHERE ID = ?
                """, String.class, body.getHeaderId());
        String detailMemo = jdbcTemplate.queryForObject("""
                SELECT MEMO
                FROM T_LOB_DETAIL
                WHERE ID = ?
                  AND HEADER_ID = ?
                """, String.class, body.getDetailId(), body.getHeaderId());

        assertThat(headerPrefix).isEqualTo("UPDATE_SAMPLE:");
        assertThat(detailMemo).isEqualTo("MyBatis updated sample data");
    }

    private Long queryLong(String sql, Object... args) {
        Number value = jdbcTemplate.queryForObject(sql, Number.class, args);
        assertThat(value).isNotNull();
        return value.longValue();
    }
}
