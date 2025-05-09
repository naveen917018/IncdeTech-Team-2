package com.incede.Repository.chargemaster;

import com.incede.Model.chargemaster.ChargeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChargeMasterRepository extends JpaRepository<ChargeMaster, Integer> {
    
    // Find a ChargeMaster by tenantId and chargeCode (case insensitive)
    Optional<ChargeMaster> findByTenantIdAndChargeCodeIgnoreCase(Integer tenantId, String chargeCode);
    
    // Find a ChargeMaster by chargeId (primary key)
    Optional<ChargeMaster> findByChargeId(Integer chargeId);

    // Custom query to check if a charge exists with the same tenantId and chargeCode (to ensure uniqueness)
    boolean existsByTenantIdAndChargeCode(Integer tenantId, String chargeCode);
}
