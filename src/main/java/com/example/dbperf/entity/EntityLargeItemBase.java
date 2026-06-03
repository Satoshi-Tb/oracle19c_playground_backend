package com.example.dbperf.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class EntityLargeItemBase {

    @Column(name = "NUMBER_ITEM1")
    private Long numberItem1;
    
    @Column(name = "NUMBER_ITEM2")
    private Long numberItem2;

    @Column(name = "NUMBER_ITEM3")
    private Long numberItem3;
    
    @Column(name = "NUMBER_ITEM4")
    private Long numberItem4;
    @Column(name = "NUMBER_ITEM5")
    private Long numberItem5;
    @Column(name = "NUMBER_ITEM6")
    private Long numberItem6;
    @Column(name = "NUMBER_ITEM7")
    private Long numberItem7;
    @Column(name = "NUMBER_ITEM8")
    private Long numberItem8;
    @Column(name = "NUMBER_ITEM9")
    private Long numberItem9;
    @Column(name = "NUMBER_ITEM10")
    private Long numberItem10;
    @Column(name = "NUMBER_ITEM11")
    private Long numberItem11;
    @Column(name = "NUMBER_ITEM12")
    private Long numberItem12;
    @Column(name = "NUMBER_ITEM13")
    private Long numberItem13;
    @Column(name = "NUMBER_ITEM14")
    private Long numberItem14;
    @Column(name = "NUMBER_ITEM15")
    private Long numberItem15;
    @Column(name = "NUMBER_ITEM16")
    private Long numberItem16;
    @Column(name = "NUMBER_ITEM17")
    private Long numberItem17;
    @Column(name = "NUMBER_ITEM18")
    private Long numberItem18;
    @Column(name = "NUMBER_ITEM19")
    private Long numberItem19;
    @Column(name = "NUMBER_ITEM20")
    private Long numberItem20;

    @Column(name = "STRING_ITEM1")
    private String stringItem1;
    @Column(name = "STRING_ITEM2")
    private String stringItem2;
    @Column(name = "STRING_ITEM3")
    private String stringItem3;
    @Column(name = "STRING_ITEM4")
    private String stringItem4;
    @Column(name = "STRING_ITEM5")
    private String stringItem5;
    @Column(name = "STRING_ITEM6")
    private String stringItem6;
    @Column(name = "STRING_ITEM7")
    private String stringItem7;
    @Column(name = "STRING_ITEM8")
    private String stringItem8;
    @Column(name = "STRING_ITEM9")
    private String stringItem9;
    @Column(name = "STRING_ITEM10")
    private String stringItem10;
    @Column(name = "STRING_ITEM11")
    private String stringItem11;
    @Column(name = "STRING_ITEM12")
    private String stringItem12;
    @Column(name = "STRING_ITEM13")
    private String stringItem13;
    @Column(name = "STRING_ITEM14")
    private String stringItem14;
    @Column(name = "STRING_ITEM15")
    private String stringItem15;
    @Column(name = "STRING_ITEM16")
    private String stringItem16;
    @Column(name = "STRING_ITEM17")
    private String stringItem17;
    @Column(name = "STRING_ITEM18")
    private String stringItem18;
    @Column(name = "STRING_ITEM19")
    private String stringItem19;
    @Column(name = "STRING_ITEM20")
    private String stringItem20;

    @Column(name = "DATE_ITEM1")
    private Date dateItem1;
    @Column(name = "DATE_ITEM2")
    private Date dateItem2;
    @Column(name = "DATE_ITEM3")
    private Date dateItem3;
    @Column(name = "DATE_ITEM4")
    private Date dateItem4;
    @Column(name = "DATE_ITEM5")
    private Date dateItem5;
    @Column(name = "DATE_ITEM6")
    private Date dateItem6;
    @Column(name = "DATE_ITEM7")
    private Date dateItem7;
    @Column(name = "DATE_ITEM8")
    private Date dateItem8;
    @Column(name = "DATE_ITEM9")
    private Date dateItem9;
    @Column(name = "DATE_ITEM10")
    private Date dateItem10;
    @Column(name = "DATE_ITEM11")
    private Date dateItem11;
    @Column(name = "DATE_ITEM12")
    private Date dateItem12;
    @Column(name = "DATE_ITEM13")
    private Date dateItem13;
    @Column(name = "DATE_ITEM14")
    private Date dateItem14;
    @Column(name = "DATE_ITEM15")
    private Date dateItem15;
    @Column(name = "DATE_ITEM16")
    private Date dateItem16;
    @Column(name = "DATE_ITEM17")
    private Date dateItem17;
    @Column(name = "DATE_ITEM18")
    private Date dateItem18;
    @Column(name = "DATE_ITEM19")
    private Date dateItem19;
    @Column(name = "DATE_ITEM20")
    private Date dateItem20;
    
    @Column(name = "CLOB_ITEM1")
    private String clobItem1;
    @Column(name = "CLOB_ITEM2")
    private String clobItem2;
    @Column(name = "CLOB_ITEM3")
    private String clobItem3;
    @Column(name = "CLOB_ITEM4")
    private String clobItem4;
    @Column(name = "CLOB_ITEM5")
    private String clobItem5;
    @Column(name = "CLOB_ITEM6")
    private String clobItem6;
    @Column(name = "CLOB_ITEM7")
    private String clobItem7;
    @Column(name = "CLOB_ITEM8")
    private String clobItem8;
    @Column(name = "CLOB_ITEM9")
    private String clobItem9;
    @Column(name = "CLOB_ITEM10")
    private String clobItem10;
    @Column(name = "CLOB_ITEM11")
    private String clobItem11;
    @Column(name = "CLOB_ITEM12")
    private String clobItem12;
    @Column(name = "CLOB_ITEM13")
    private String clobItem13;
    @Column(name = "CLOB_ITEM14")
    private String clobItem14;
    @Column(name = "CLOB_ITEM15")
    private String clobItem15;
    @Column(name = "CLOB_ITEM16")
    private String clobItem16;
    @Column(name = "CLOB_ITEM17")
    private String clobItem17;
    @Column(name = "CLOB_ITEM18")
    private String clobItem18;
    @Column(name = "CLOB_ITEM19")
    private String clobItem19;
    @Column(name = "CLOB_ITEM20")
    private String clobItem20;
    
    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "CREATED_BY", length = 100)
    private String createdBy;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "UPDATED_BY", length = 100)
    private String updatedBy;
}
