package com.incede.Model.standardweight.standardweightmaster;

import java.math.BigDecimal;
import java.util.UUID;

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
import lombok.Data;

@Entity
@Table(name = "standard_weight_master", schema = "master_data", indexes = {
		@Index(name = "idx_ornament_id", columnList = "ornament_id"),
		@Index(name = "idx_tenant_id", columnList = "tenant_id"),
		@Index(name = "idx_is_active", columnList = "is_active") }, uniqueConstraints = @UniqueConstraint(columnNames = {
				"ornament_id", "tenant_id" }))
@Data
public class StandardWeightMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "weight_id")
	private Integer weightId;

	@Column(name = "ornament_id", nullable = false)
	private Integer ornamentId;

	@Column(name = "standard_weight", nullable = false, precision = 10, scale = 2)
	private BigDecimal standardWeight;

	@Column(name = "tenant_id", nullable = false)
	private Integer tenantId;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "uuid", nullable = false, unique = true)
	private UUID uuid;

	@PrePersist
	public void generateIdentity() {
		if (uuid == null) {
			uuid = UUID.randomUUID();
		}
	}
}
