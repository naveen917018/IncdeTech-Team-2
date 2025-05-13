package com.incede.Model.recoveryParameter;

import java.util.UUID;

import org.hibernate.annotations.Check;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
    name = "recovery_parameter_master",
    schema = "master_data",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_tenant_param_name",
        columnNames = {"tenant_id", "param_name"}
    ),
    indexes = {
        @Index(name = "idx_tenant_id", columnList = "tenant_id"),
        @Index(name = "idx_param_name", columnList = "param_name"),
        @Index(name = "idx_is_active", columnList = "is_active")
    }
)
@Check(constraints = "param_data_type IN ('string','number','boolean','date','array','object')")
public class RecoveryParameterMaster extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recovery_param_id")
    private Integer recoveryParamId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "param_name", nullable = false)
    private String paramName;

    @Column(name = "param_value", columnDefinition = "TEXT", nullable = false)
    private String paramValue;

    @Column(name = "param_data_type", nullable = false)
    private String paramDataType = "string";

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "identity", nullable = false, unique = true)
    private String identity;
}
