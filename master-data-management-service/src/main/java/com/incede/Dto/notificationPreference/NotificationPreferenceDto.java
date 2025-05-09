package com.incede.Dto.notificationPreference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationPreferenceDto {
	
	private Integer preferenceId;
	private Integer tenantId;
	private String preferenceType;
	private Boolean isActive;
	private Integer createdBy;
    private Integer updatedBy;
    private Boolean isDeleted;

}
