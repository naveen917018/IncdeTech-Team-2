package com.incede.Dto.taxCategory;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxCategoryDto {
	
	private Integer taxCategoryId;
	@NotNull(message = "tenantId cannot be null")
    private Integer tenantId;
	@NotNull(message = "Tax category name cannot be null")
    private String name;
    private Boolean active;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;

}
