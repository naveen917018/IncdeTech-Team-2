package com.incede.Dto.taxCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxCategoryDto {
	
	private Integer taxCategoryId;
    private Integer tenantId;
    private String name;
    private Boolean active;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;

}
