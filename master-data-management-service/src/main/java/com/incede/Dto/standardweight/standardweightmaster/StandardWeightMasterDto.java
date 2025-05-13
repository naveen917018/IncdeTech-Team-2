package com.incede.Dto.standardweight.standardweightmaster;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardWeightMasterDto {
	
	private Integer weightId;
	 @Column(nullable = false)
	private Integer ornamentId;
	 @Column(nullable = false)
	private BigDecimal standardWeight;
	 @Column(nullable = false)
	private Integer tenantId;
	private Boolean isActive;
	 @Column(nullable = false)
	private String uuid;
	private Integer createdBy;
	private Integer updatedBy;
	private Boolean isDeleted;
}
