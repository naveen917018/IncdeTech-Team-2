package com.incede.Dto.liability.liabilityProduct;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiabilityProductDto {

	
    private Integer productId;
    private Integer tenantId;
    private String productName;
    private String productCode;
    private String productDescription;
    private Boolean isActive;
    private Boolean isDeleted;
    private Integer createdBy;
    private Integer updatedBy;

}
