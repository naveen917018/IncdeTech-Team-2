package com.incede.Dto.standardweight.standardweightmaster;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardWeightMasterDto {
	
	private Integer weightId;
	 @NotNull(message = "OrnamentId should not be null")
	private Integer ornamentId;
	 @NotNull(message = "StandardWeight should not be null")
	private BigDecimal standardWeight;
	 @NotNull(message = "TenantId should not be null")
	private Integer tenantId;
	private Boolean isActive;
	 @NotNull(message = "Uuid should not be null")
	private String uuid;
	private Integer createdBy;
	private Integer updatedBy;
	private Boolean isDeleted;
}
