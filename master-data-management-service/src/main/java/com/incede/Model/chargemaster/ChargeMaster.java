package com.incede.Model.chargemaster;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.Check;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(
    name = "charge_master",
    schema = "master_data",
    uniqueConstraints = @UniqueConstraint(
        name = "uc_charge_code", // Matches SQL constraint name
        columnNames = {"tenant_id", "charge_code"}
    ),
    indexes = {
        @Index(name = "idx_tenant_charge", columnList = "tenant_id"),
        @Index(name = "idx_charge_code", columnList = "charge_code"),
        @Index(name = "idx_gl_account", columnList = "gl_account_id")
    }
)
@Check(constraints = "charge_type IN ('Fixed', 'Percentage')")
@Check(constraints = "charge_amount >= 0")
public class ChargeMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "charge_id")
    private Integer chargeId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "charge_name", nullable = false)
    private String chargeName;

    @Column(name = "charge_code", nullable = false)
    private String chargeCode;

    @DecimalMin(value = "0.00", inclusive = true, message = "Charge amount must be non-negative")
    @Column(name = "charge_amount", nullable = false)
    private BigDecimal chargeAmount;

    @Column(name = "charge_type", nullable = false)
    private String chargeType; // Only 'Fixed' or 'Percentage'

    @Column(name = "gl_account_id", nullable = false)
    private Integer glAccountId;

    @Column(name = "identity", nullable = false, unique = true)
    private UUID identity;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PrePersist
    private void prePersist() {
        if (this.identity == null) {
            this.identity = UUID.randomUUID();
        }
    }
}
