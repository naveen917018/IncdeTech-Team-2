package com.incede.Model.liability.liabilityProductMaster;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "liability_product_master", schema = "master_data",
uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "product_code"}),
indexes = {
    @Index(name = "idx_tenant_product", columnList = "tenant_id, product_code"),
    @Index(name = "idx_product_name", columnList = "product_name")
})
public class LiabilityProductMaster extends BaseEntity {
	 	
	
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id",nullable = false)
    private Integer productId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "is_active")
    private Boolean isActive = true;


}
