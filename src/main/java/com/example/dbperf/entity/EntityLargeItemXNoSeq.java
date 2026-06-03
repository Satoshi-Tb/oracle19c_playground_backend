package com.example.dbperf.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "T_LARGE_ITEM_X")
@Data
@EqualsAndHashCode(callSuper=false)
public class EntityLargeItemXNoSeq extends EntityLargeItemBase{

    @Id
    private Long id;
}
