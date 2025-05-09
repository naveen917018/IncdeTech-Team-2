package com.incede.Repository.Tax;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Model.Tax.TaxMaster;

public interface TaxMasterRepository extends JpaRepository<TaxMaster, Integer> {
    Optional<TaxMaster> findByTenantIdAndTaxNameIgnoreCase(Integer tenantId, String taxName);
}
