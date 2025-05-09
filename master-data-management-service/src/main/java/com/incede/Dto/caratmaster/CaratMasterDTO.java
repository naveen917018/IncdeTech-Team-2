package com.incede.Dto.caratmaster;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaratMasterDTO {

    private Integer caratId;
    private Integer tenantId;
    private String caratValue;
    private BigDecimal purityPercentage;
    private Boolean isActive;
    private String UUID;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
}
