package com.incede.Dto.notificationPreference;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferenceDto {
	
	private Integer preferenceId;
	@NotNull(message = "tenantId cannot be null")
	private Integer tenantId;
	@NotNull(message = "preference Type cannot be null")
	private String preferenceType;
	private Boolean isActive;
	private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;

}
