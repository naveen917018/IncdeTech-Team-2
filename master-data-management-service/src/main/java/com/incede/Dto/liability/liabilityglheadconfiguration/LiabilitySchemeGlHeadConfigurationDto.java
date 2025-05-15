package com.incede.Dto.liability.liabilityglheadconfiguration;

import com.incede.Enum.GLAccountType;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LiabilitySchemeGlHeadConfigurationDto {

	private Integer glConfigId;
	@NotNull(message = "SchemeId should not be null")
	private Integer schemeId;
	@NotNull(message = "GlAccountType should not be null")
	private GLAccountType glAccountType;
	@NotNull(message = "GlAccountId should not be null")
	private Integer glAccountId;
	private Integer createdBy;
	private Integer updatedBy;
	private Boolean isDeleted;
}
