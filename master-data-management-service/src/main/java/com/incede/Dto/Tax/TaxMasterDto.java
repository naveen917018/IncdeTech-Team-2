package com.incede.Dto.Tax;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxMasterDto {

    private Integer taxId;               // Auto-generated, not user-editable
    private Integer tenantId;           // Must be provided
    private String taxName;             // Must be unique per tenant
    private BigDecimal taxRate;         // Must be between 0.00 and 100.00
    private Integer glAccountId;        // Must be a valid GL account
    private Boolean isActive;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
    private UUID identity;              // Optional unique identity for reference
}
