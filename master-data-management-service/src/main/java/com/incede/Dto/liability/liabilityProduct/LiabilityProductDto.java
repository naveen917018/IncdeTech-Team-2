package com.incede.Dto.liability.liabilityProduct;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LiabilityProductDto {

	
    private Integer productId;
    
    @NotNull(message ="Tenant id should not be null !")
    private Integer tenantId;
    
    @NotNull(message ="Product Name should not be null !")
    private String productName;
    
    @NotNull(message ="Product Code should not be null !")
    private String productCode;
    
    @NotNull(message ="Product Description should not be null !")
    private String productDescription;
    
    private Boolean isActive;   
    private Boolean isDeleted;
    private Integer createdBy;
    private Integer updatedBy;

}
