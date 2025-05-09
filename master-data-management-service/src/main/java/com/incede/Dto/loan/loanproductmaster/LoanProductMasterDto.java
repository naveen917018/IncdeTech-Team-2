package com.incede.Dto.loan.loanproductmaster;

import java.time.LocalDateTime;
import java.util.UUID;

import com.incede.Dto.loan.loanpurposemaster.LoanPurposeMasterDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanProductMasterDto {
	
    private Integer productId;
    private Integer tenantId;
    private String productName;
    private String productCode;
    private Integer loanCategoryId;
    private Boolean isActive;
    private String UUID;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
}
