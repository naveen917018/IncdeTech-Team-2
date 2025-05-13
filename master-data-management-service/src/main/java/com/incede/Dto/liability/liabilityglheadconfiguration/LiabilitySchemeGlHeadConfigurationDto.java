package com.incede.Dto.liability.liabilityglheadconfiguration;

import com.incede.Enum.GLAccountType;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class LiabilitySchemeGlHeadConfigurationDto {

		
	  private Integer glConfigId;
	  @Column(nullable = false)
	  private Integer schemeId;
	  @Column(nullable = false)
	  private GLAccountType glAccountType;
	  @Column(nullable = false)
	  private Integer glAccountId;
	  private Integer createdBy;
	  private Integer updatedBy;
	  private Boolean isDeleted;
}
