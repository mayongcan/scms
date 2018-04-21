package com.scms.modules.order.entity;

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
@Table(name = "scms_order_tag")
public class ScmsOrderTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @Column(name = "ORDER_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long orderId;

    @Id
    @NonNull
    @Column(name = "TAG_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long tagId;

}
