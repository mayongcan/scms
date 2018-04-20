package com.scms.modules.goods.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scms_goods_tag")
public class ScmsGoodsTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @Column(name = "GOODS_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long goodsId;

    @Id
    @NonNull
    @Column(name = "TAG_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long tagId;

}
