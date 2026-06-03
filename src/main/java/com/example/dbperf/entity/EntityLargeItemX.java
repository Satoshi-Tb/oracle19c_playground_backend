package com.example.dbperf.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "T_LARGE_ITEM_X")
@Data
@EqualsAndHashCode(callSuper=false)
public class EntityLargeItemX extends EntityLargeItemBase{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "large_item_seq_gen")
    @SequenceGenerator(name = "large_item_seq_gen", sequenceName = "SEQ_T_LARGE_ITEM_X", allocationSize = 1)
    private Long id;
}
