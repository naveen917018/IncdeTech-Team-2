package com.incede.Model.payment.paymentmodemaster;

import com.incede.Model.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "payment_mode_master", schema = "master_data", indexes = {
		@Index(name = "idx_payment_mode_name", columnList = "payment_mode_name") }, uniqueConstraints = {
				@UniqueConstraint(name = "uc_payment_mode_name", columnNames = { "tenant_id", "payment_mode_name" }) })
public class PaymentModeMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_mode_id")
	private Integer paymentModeId;

	@Column(name = "tenant_id", nullable = false)
	private Integer tenantId;

	@Column(name = "payment_mode_name", nullable = false)
	private String paymentModeName;

	@Column(name = "payment_mode_code")
	private String paymentModeCode;

	@Column(name = "is_online", nullable = false)
	private Boolean isOnline = false;

	@Column(name = "requires_reference", nullable = false)
	private Boolean requiresReference = false;

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true;

	@Column(name = "identity", nullable = false, unique = true)
	private UUID identity;

}
