package com.incede.Dto.document.documentconfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequirementConfigurationDto {
    
	private Integer documentId;
    private Integer tenantId;
    private Integer productId;
    private String documentType;
    private Boolean isMandatory;
    private String documentCategory;
    private Integer sequenceNumber;
    private Boolean isActive;
    private UUID identity;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;
}
