package com.example.dbperf.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.dbperf.model.LobDetail;
import com.example.dbperf.model.LobHeader;

@Mapper
public interface LobMapper {

    int insertHeader(LobHeader header);

    int insertDetail(LobDetail detail);

    int updateHeader(LobHeader header);

    int updateDetail(LobDetail detail);
}
