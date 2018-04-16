package com.scms.modules.user.entity;

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
@Table(name = "scms_user_commission")
public class ScmsUserCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NonNull
    @Column(name = "USER_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long userId;

    @Id
    @NonNull
    @Column(name = "COMMISSION_ID", unique = true, nullable = false, precision = 10, scale = 0)
    private Long commissionId;

}
