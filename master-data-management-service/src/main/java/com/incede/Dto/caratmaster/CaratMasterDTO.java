package com.incede.Dto.caratmaster;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaratMasterDTO {

    private Integer caratId;
    
    @NotNull(message="Tenant Id should not be null")
    private Integer tenantId;
    
    @NotNull(message="Carat Value should not be null")
    private String caratValue;
    
    @NotNull(message="Purity Percentage should not be null")
    private BigDecimal purityPercentage;
    private Boolean isActive;
    
    @NotNull(message="UUID should not be null")
    private String UUID;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
}
