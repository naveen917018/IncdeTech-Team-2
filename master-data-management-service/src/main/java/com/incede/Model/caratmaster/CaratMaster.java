package com.incede.Model.caratmaster;

import java.math.BigDecimal;

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
		name = "carat_master", 
		schema = "master_data",
		uniqueConstraints = {
						@UniqueConstraint(columnNames = {"tenant_id", "carat_value"})
						},
		indexes = {
		    @Index(name = "idx_tenant_id", columnList = "tenant_id"),
		    @Index(name = "idx_is_active", columnList = "is_active")
		}
)

public class CaratMaster extends BaseEntity{

	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "carat_id")
	    private Integer caratId;

	    @Column(name = "tenant_id", nullable = false)
	    private Integer tenantId;

	    @Column(name = "carat_value", nullable = false)
	    private String caratValue;

	    @Column(name = "purity_percentage", nullable = false, precision = 5, scale = 2)
	    private BigDecimal purityPercentage;

	    @Column(name = "is_active")
	    private Boolean isActive = true;

	    @Column(name = "uuid", nullable = false, unique = true)
	    private String UUID;

}
