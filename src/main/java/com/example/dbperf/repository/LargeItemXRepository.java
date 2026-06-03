package com.example.dbperf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.dbperf.entity.EntityLargeItemX;

public interface LargeItemXRepository extends JpaRepository<EntityLargeItemX, Long>{
    // ネイティブクエリを使用してTRUNCATE TABLEを実行
    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE T_LARGE_ITEM_X", nativeQuery = true)
    void truncate();
    
    // シーケンス番号の採番
    @Query(value = "SELECT SEQ_T_LARGE_ITEM_X.NEXTVAL FROM DUAL CONNECT BY LEVEL <= :count", nativeQuery = true)
    List<Long> getSeqList(@Param("count") Integer count);
}
