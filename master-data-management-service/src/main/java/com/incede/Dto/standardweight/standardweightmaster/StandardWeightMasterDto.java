package com.incede.Dto.standardweight.standardweightmaster;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardWeightMasterDto {
	
	private Integer weightId;
	private Integer ornamentId;
	private BigDecimal standardWeight;
	private Integer tenantId;
	private Boolean isActive;
	private String uuid;
	private Integer createdBy;
	private Integer updatedBy;
	private Boolean isDeleted;
}
