package com.incede.Repository.caratmaster;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Dto.caratmaster.CaratMasterDTO;
import com.incede.Model.caratmaster.CaratMaster;

public interface CaratMasterRepository extends JpaRepository<CaratMaster, Integer>{

	List<CaratMaster> findByTenantId(Integer tenantId);

	boolean existsByTenantIdAndCaratValue(Integer tenantId, String caratValue);

	boolean existsByTenantIdAndCaratValueIgnoreCaseAndIsDeletedFalse(Integer tenantId, String caratValue);

}
