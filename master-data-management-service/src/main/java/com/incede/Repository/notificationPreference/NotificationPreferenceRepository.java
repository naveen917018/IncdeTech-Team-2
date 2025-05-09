package com.incede.Repository.notificationPreference;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.notificationPreference.NotificationPreference;

@Repository
public interface NotificationPreferenceRepository extends JpaRepository<NotificationPreference, Integer> {
//    boolean existsByTenantIdAndPreferenceType(Integer tenantId, String preferenceType);
	// CREATE-time: any active row with same tenantId & preferenceType (ignore case)?
    boolean existsByTenantIdAndPreferenceTypeIgnoreCaseAndIsDeletedFalse(Integer tenantId,String preferenceType);
 // UPDATE-time: same, but exclude the record itself
    boolean existsByTenantIdAndPreferenceTypeIgnoreCaseAndPreferenceIdNotAndIsDeletedFalse(
        Integer tenantId,
        String preferenceType,
        Integer preferenceId
    );
    List<NotificationPreference> findAllByTenantId(Integer tenantId);
    Optional<NotificationPreference> findByPreferenceIdAndIsDeletedFalse(Integer id);
    List<NotificationPreference> findAllByTenantIdAndIsDeletedFalse(Integer tenantId);
    List<NotificationPreference> findAllByIsDeletedFalse();
}
