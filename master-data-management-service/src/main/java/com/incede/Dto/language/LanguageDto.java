package com.incede.Dto.language;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LanguageDto {

    private Integer languageId;
    private Integer tenantId;
    private String languageName;
    private Boolean isActive;
    private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;

}
