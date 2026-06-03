package com.example.dbperf.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dbperf.entity.EntityLargeItemBase;
import com.example.dbperf.entity.EntityLargeItemX;
import com.example.dbperf.entity.EntityLargeItemXNoSeq;
import com.example.dbperf.repository.LargeItemXRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JPASampleService {

	private static final int BATCH_SIZE = 50;
    private static final String SEDDS_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789あいうえおかきくけこさしすせそ";

	
	private Random random = new Random();
	
	@Autowired
	private LargeItemXRepository largeItemXRepository;
	
    @PersistenceContext
    private EntityManager entityManager;  // EntityManagerを追加

	@Transactional
	public Integer createSampleDataByJPABatch(Integer totalCount) {
	    log.info("Truncate Data Start: " + LocalDateTime.now());
	    largeItemXRepository.truncate();
	    log.info("Truncate Data End: " + LocalDateTime.now());
	
	    log.info("Create Sample Data Start: " + LocalDateTime.now());
	    var sampleData = createSampleDataList(totalCount);
	    log.info("Create Sample Data End: " + LocalDateTime.now());
	    
	    log.info("Insert Data By JPA Batch Start: " + LocalDateTime.now());
	    List<EntityLargeItemX> batchList = new ArrayList<>();
	    
	    sampleData.forEach(item -> {
	    	batchList.add(item);
	        if (batchList.size() == BATCH_SIZE) {
	        	largeItemXRepository.saveAll(batchList);
	        	
	        	// フラッシュ処理追加。更新に速度影響はなかった
	        	entityManager.flush();
	        	entityManager.clear();
	            batchList.clear(); // 保存後にリストをクリア
	        }
	    });
	    
	    // 残りのデータを挿入
	    if (!batchList.isEmpty()) {
	    	largeItemXRepository.saveAll(batchList);
        	entityManager.flush();
        	entityManager.clear();
	    	batchList.clear();
	    }

	    log.info("Insert Data By JPA Batch End: " + LocalDateTime.now());
	    
	    return totalCount;
	}
	
    @Transactional
    public Integer createSampleDataByPersistBatch(Integer totalCount) {
        log.info("Truncate Data Start: " + LocalDateTime.now());
        largeItemXRepository.truncate();
        log.info("Truncate Data End: " + LocalDateTime.now());

        log.info("Create Sample Data Start: " + LocalDateTime.now());
        var sampleData = createSampleDataList(totalCount);
        log.info("Create Sample Data End: " + LocalDateTime.now());

        log.info("Insert Data By Persist Batch Start: " + LocalDateTime.now());

        int insertedCount = 0;
        for (int i = 0; i < sampleData.size(); i++) {
        	var e = sampleData.get(i);
            entityManager.persist(e);  // persistメソッドでエンティティを永続化

            if (i > 0 && i % BATCH_SIZE == 0) {
                entityManager.flush(); // 永続コンテキストの内容をDBに反映
                entityManager.clear(); // 永続コンテキストをクリアしてメモリ消費を抑える
                insertedCount += BATCH_SIZE;
                // log.info("Inserted " + insertedCount + " records at " + LocalDateTime.now());
            }
        }

        // 残りのデータを挿入
        if (sampleData.size() % BATCH_SIZE != 0) {
            entityManager.flush();
            entityManager.clear();
            insertedCount += sampleData.size() % BATCH_SIZE;
            // log.info("Inserted remaining " + (sampleData.size() % BATCH_SIZE) + " records.");
        }

        log.info("Insert Data By Persist Batch End: " + LocalDateTime.now());
        
        return totalCount;
    }
	
    @Transactional
    public Integer createSampleDataByNativeQuery(Integer totalCount) {
        log.info("Truncate Data Start: " + LocalDateTime.now());
        largeItemXRepository.truncate();
        log.info("Truncate Data End: " + LocalDateTime.now());

        log.info("Create Sample Data Start: " + LocalDateTime.now());
        var sampleData = createSampleDataNoSeqList(totalCount);
        log.info("Create Sample Data End: " + LocalDateTime.now());
    	
        log.info("Insert Data By NativeQuery Start: " + LocalDateTime.now());
        for (int i = 0; i < totalCount; i += BATCH_SIZE) {
            int end = Math.min(i + BATCH_SIZE, sampleData.size());
            var chunk = sampleData.subList(i, end);
            Query q = entityManager.createNativeQuery(createNativeQuery(chunk.size()));
            setParameter(q, chunk);
            q.executeUpdate();
        }
        log.info("Insert Data By NativeQuery End: " + LocalDateTime.now());

        return totalCount;
    }
    
    private String createNativeQuery(int batchCount) {
    	StringBuilder sb = new StringBuilder("INTO T_LARGE_ITEM_X (");
    	sb.append("ID,");
    	sb.append("NUMBER_ITEM1,");
    	sb.append("NUMBER_ITEM2,");
    	sb.append("NUMBER_ITEM3,");
    	sb.append("NUMBER_ITEM4,");
    	sb.append("NUMBER_ITEM5,");
    	sb.append("NUMBER_ITEM6,");
    	sb.append("NUMBER_ITEM7,");
    	sb.append("NUMBER_ITEM8,");
    	sb.append("NUMBER_ITEM9,");
    	sb.append("NUMBER_ITEM10,");
    	sb.append("NUMBER_ITEM11,");
    	sb.append("NUMBER_ITEM12,");
    	sb.append("NUMBER_ITEM13,");
    	sb.append("NUMBER_ITEM14,");
    	sb.append("NUMBER_ITEM15,");
    	sb.append("NUMBER_ITEM16,");
    	sb.append("NUMBER_ITEM17,");
    	sb.append("NUMBER_ITEM18,");
    	sb.append("NUMBER_ITEM19,");
    	sb.append("NUMBER_ITEM20,");
    	sb.append("STRING_ITEM1,");
    	sb.append("STRING_ITEM2,");
    	sb.append("STRING_ITEM3,");
    	sb.append("STRING_ITEM4,");
    	sb.append("STRING_ITEM5,");
    	sb.append("STRING_ITEM6,");
    	sb.append("STRING_ITEM7,");
    	sb.append("STRING_ITEM8,");
    	sb.append("STRING_ITEM9,");
    	sb.append("STRING_ITEM10,");
    	sb.append("STRING_ITEM11,");
    	sb.append("STRING_ITEM12,");
    	sb.append("STRING_ITEM13,");
    	sb.append("STRING_ITEM14,");
    	sb.append("STRING_ITEM15,");
    	sb.append("STRING_ITEM16,");
    	sb.append("STRING_ITEM17,");
    	sb.append("STRING_ITEM18,");
    	sb.append("STRING_ITEM19,");
    	sb.append("STRING_ITEM20,");
    	sb.append("DATE_ITEM1,");
    	sb.append("DATE_ITEM2,");
    	sb.append("DATE_ITEM3,");
    	sb.append("DATE_ITEM4,");
    	sb.append("DATE_ITEM5,");
    	sb.append("DATE_ITEM6,");
    	sb.append("DATE_ITEM7,");
    	sb.append("DATE_ITEM8,");
    	sb.append("DATE_ITEM9,");
    	sb.append("DATE_ITEM10,");
    	sb.append("DATE_ITEM11,");
    	sb.append("DATE_ITEM12,");
    	sb.append("DATE_ITEM13,");
    	sb.append("DATE_ITEM14,");
    	sb.append("DATE_ITEM15,");
    	sb.append("DATE_ITEM16,");
    	sb.append("DATE_ITEM17,");
    	sb.append("DATE_ITEM18,");
    	sb.append("DATE_ITEM19,");
    	sb.append("DATE_ITEM20,");
    	sb.append("CLOB_ITEM1,");
    	sb.append("CLOB_ITEM2,");
    	sb.append("CLOB_ITEM3,");
    	sb.append("CLOB_ITEM4,");
    	sb.append("CLOB_ITEM5,");
    	sb.append("CLOB_ITEM6,");
    	sb.append("CLOB_ITEM7,");
    	sb.append("CLOB_ITEM8,");
    	sb.append("CLOB_ITEM9,");
    	sb.append("CLOB_ITEM10,");
    	sb.append("CLOB_ITEM11,");
    	sb.append("CLOB_ITEM12,");
    	sb.append("CLOB_ITEM13,");
    	sb.append("CLOB_ITEM14,");
    	sb.append("CLOB_ITEM15,");
    	sb.append("CLOB_ITEM16,");
    	sb.append("CLOB_ITEM17,");
    	sb.append("CLOB_ITEM18,");
    	sb.append("CLOB_ITEM19,");
    	sb.append("CLOB_ITEM20");
    	sb.append(") VALUES (");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?,");
    	sb.append("?");
    	sb.append(") ");
    	
    	String insertStmt = sb.toString();
    	StringBuilder q = new StringBuilder("INSERT ALL ");
    	for (int i = 0; i < batchCount; i++) {
    		q.append(insertStmt);
    	}
    	q.append(" SELECT * FROM DUAL");
    	
    	return q.toString();
    }
    
    private void setParameter(Query q, List<EntityLargeItemXNoSeq> entities) {

    	var itemCnt = 81;
    	for (int i = 0; i < entities.size(); i++) {
        	int j = 1;
    		var e = entities.get(i);
    		q.setParameter(itemCnt * i + j++ , e.getId());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem1());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem2());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem3());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem4());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem5());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem6());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem7());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem8());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem9());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem10());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem11());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem12());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem13());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem14());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem15());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem16());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem17());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem18());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem19());
    		q.setParameter(itemCnt * i + j++ , e.getNumberItem20());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem1());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem2());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem3());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem4());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem5());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem6());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem7());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem8());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem9());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem10());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem11());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem12());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem13());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem14());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem15());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem16());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem17());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem18());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem19());
    		q.setParameter(itemCnt * i + j++ , e.getStringItem20());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem1());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem2());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem3());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem4());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem5());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem6());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem7());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem8());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem9());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem10());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem11());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem12());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem13());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem14());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem15());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem16());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem17());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem18());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem19());
    		q.setParameter(itemCnt * i + j++ , e.getDateItem20());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem1());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem2());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem3());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem4());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem5());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem6());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem7());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem8());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem9());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem10());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem11());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem12());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem13());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem14());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem15());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem16());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem17());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem18());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem19());
    		q.setParameter(itemCnt * i + j++ , e.getClobItem20());
    	}
    }
    
	private List<EntityLargeItemX> createSampleDataList(Integer totalCount) {
		List<EntityLargeItemX> result = new ArrayList<>();
		var randomLargeStr = generateRandomString(10000);
		
		// 末尾を含む
		IntStream.rangeClosed(1 ,totalCount).forEach(num -> {
			var e = new EntityLargeItemX();
			setData(e, randomLargeStr);
			result.add(e);
		});
		
		return result;
	}
	
	private List<EntityLargeItemXNoSeq> createSampleDataNoSeqList(Integer totalCount) {
		List<EntityLargeItemXNoSeq> result = new ArrayList<>();
		var randomLargeStr = generateRandomString(10000);
		var seqList = largeItemXRepository.getSeqList(totalCount);
		
		// 末尾を含む
		IntStream.rangeClosed(1 ,totalCount).forEach(num -> {
			var e = new EntityLargeItemXNoSeq();
			setData(e, randomLargeStr);
			e.setId(seqList.get(num - 1));
			result.add(e);
		});
		
		return result;
	}
	
	private void setData(EntityLargeItemBase e, String randomLargeStr) {
		e.setNumberItem1(10000l);
		e.setNumberItem2(10000l);
		e.setNumberItem3(10000l);
		e.setNumberItem4(10000l);
		e.setNumberItem5(10000l);
		e.setNumberItem6(10000l);
		e.setNumberItem7(10000l);
		e.setNumberItem8(10000l);
		e.setNumberItem9(10000l);
		e.setNumberItem10(10000l);
		e.setNumberItem11(10000l);
		e.setNumberItem12(10000l);
		e.setNumberItem13(10000l);
		e.setNumberItem14(10000l);
		e.setNumberItem15(10000l);
		e.setNumberItem16(10000l);
		e.setNumberItem17(10000l);
		e.setNumberItem18(10000l);
		e.setNumberItem19(10000l);
		e.setNumberItem20(10000l);
		e.setStringItem1("STRING_ITEM1");
		e.setStringItem2("STRING_ITEM2");
		e.setStringItem3("STRING_ITEM3");
		e.setStringItem4("STRING_ITEM4");
		e.setStringItem5("STRING_ITEM5");
		e.setStringItem6("STRING_ITEM6");
		e.setStringItem7("STRING_ITEM7");
		e.setStringItem8("STRING_ITEM8");
		e.setStringItem9("STRING_ITEM9");
		e.setStringItem10("STRING_ITEM10");
		e.setStringItem11("STRING_ITEM11");
		e.setStringItem12("STRING_ITEM12");
		e.setStringItem13("STRING_ITEM13");
		e.setStringItem14("STRING_ITEM14");
		e.setStringItem15("STRING_ITEM15");
		e.setStringItem16("STRING_ITEM16");
		e.setStringItem17("STRING_ITEM17");
		e.setStringItem18("STRING_ITEM18");
		e.setStringItem19("STRING_ITEM19");
		e.setStringItem20("STRING_ITEM20");
		e.setDateItem1(new Date());
		e.setDateItem2(new Date());
		e.setDateItem3(new Date());
		e.setDateItem4(new Date());
		e.setDateItem5(new Date());
		e.setDateItem6(new Date());
		e.setDateItem7(new Date());
		e.setDateItem8(new Date());
		e.setDateItem9(new Date());
		e.setDateItem10(new Date());
		e.setDateItem11(new Date());
		e.setDateItem12(new Date());
		e.setDateItem13(new Date());
		e.setDateItem14(new Date());
		e.setDateItem15(new Date());
		e.setDateItem16(new Date());
		e.setDateItem17(new Date());
		e.setDateItem18(new Date());
		e.setDateItem19(new Date());
		e.setDateItem20(new Date());
		e.setClobItem1(randomLargeStr);
		e.setClobItem2(randomLargeStr);
		e.setClobItem3(randomLargeStr);
		e.setClobItem4(randomLargeStr);
		e.setClobItem5(randomLargeStr);
		e.setClobItem6(randomLargeStr);
		e.setClobItem7(randomLargeStr);
		e.setClobItem8(randomLargeStr);
		e.setClobItem9(randomLargeStr);
		e.setClobItem10(randomLargeStr);
		e.setClobItem11(randomLargeStr);
		e.setClobItem12(randomLargeStr);
		e.setClobItem13(randomLargeStr);
		e.setClobItem14(randomLargeStr);
		e.setClobItem15(randomLargeStr);
		e.setClobItem16(randomLargeStr);
		e.setClobItem17(randomLargeStr);
		e.setClobItem18(randomLargeStr);
		e.setClobItem19(randomLargeStr);
		e.setClobItem20(randomLargeStr);
        e.setCreatedAt(new Date()); // 現在日時
        e.setCreatedBy("System");
        e.setUpdatedAt(new Date()); // 現在日時
        e.setUpdatedBy("System");
	}
	
	private String generateRandomString(long length) {
	    StringBuilder sb = new StringBuilder();
	    
		LongStream.range(0, length).forEach(num -> sb.append(SEDDS_CHARS.charAt(random.nextInt(SEDDS_CHARS.length()))));

	    return sb.toString();
	}
}
