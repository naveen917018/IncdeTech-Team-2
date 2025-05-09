package com.incede.Model.notificationPreference;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "notification_preferences",
    schema = "master_data",
    uniqueConstraints = {
        @UniqueConstraint(name ="uc_notification_preference",columnNames = {"tenant_id", "preference_type"})
    },
	indexes = {
	        @Index(name = "idx_preference_type", columnList = "preference_type")
	    }
)

public class NotificationPreference extends BaseEntity{
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	@Column(name="preference_id")
	    private Integer preferenceId;

	    @Column(name="tenant_id",nullable = false)
	    private Integer tenantId;

	    @Column(name="preference_type",nullable = false)
	    private String preferenceType;

	    @Column(name="is_active")
	    private Boolean isActive = true;

}
