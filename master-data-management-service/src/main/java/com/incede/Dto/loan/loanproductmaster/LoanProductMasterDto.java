package com.incede.Dto.loan.loanproductmaster;

import java.time.LocalDateTime;
import java.util.UUID;

import com.incede.Dto.loan.loanpurposemaster.LoanPurposeMasterDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductMasterDto {
	
    private Integer productId;
    
    @NotNull(message = "Tenant Id should not be null")
    private Integer tenantId;
    
    @NotNull(message = "Product Name should not be null")
    private String productName;
    
    @NotNull(message = "Product Code should not be null")
    private String productCode;
    
    @NotNull(message = "Loan Category Id should not be null")
    private Integer loanCategoryId;
    
    private Boolean isActive;
    
    private UUID UUID;
    
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
