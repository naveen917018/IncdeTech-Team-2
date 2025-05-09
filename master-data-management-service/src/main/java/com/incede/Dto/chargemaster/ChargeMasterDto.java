package com.incede.Dto.chargemaster;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeMasterDto {
    private Integer chargeId;
    private Integer tenantId;
    private String chargeName;
    private String chargeCode;
    private BigDecimal chargeAmount;
    private String chargeType; // Must be "Fixed" or "Percentage"
    private Integer glAccountId;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
}
