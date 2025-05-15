package com.incede.Dto.recoveryParameter;

import com.incede.validation.validParam.ParamValueValidator;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryParameterMasterDTO {


	// for updates only; POST method won’t require it
    private Integer recoveryParamId;

    @NotNull(message = "tenantId cannot be null")
    private Integer tenantId;

    @NotNull(message = "paramName cannot be null")
    private String paramName;

    @NotNull(message = "paramValue cannot be null")
    private String paramValue;

    @NotNull(message = "paramType cannot be null")
    private String paramDataType;  // must be one of "Reminder Days", "Write-off Threshold", "Recovery Escalation Rule"

    private Boolean isActive;
//    @NotNull(message = "identity cannot be null")
//    private UUID identity;
    private Integer createdBy;
    private Integer updatedBy;
}
