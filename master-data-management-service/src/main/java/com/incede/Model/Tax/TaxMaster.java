package com.incede.Model.Tax;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;

import com.incede.Model.baseentity.BaseEntity;

@Entity
@Table(name = "tax_master", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"tenant_id", "tax_name"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private Integer taxId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "tax_name", nullable = false)
    private String taxName;

    @Column(name = "tax_rate", nullable = false)
    private BigDecimal taxRate;

    @Column(name = "gl_account_id", nullable = false)
    private Integer glAccountId;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    

}
