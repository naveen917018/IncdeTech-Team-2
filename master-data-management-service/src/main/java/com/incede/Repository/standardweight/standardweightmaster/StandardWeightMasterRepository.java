package com.incede.Repository.standardweight.standardweightmaster;

import java.math.BigDecimal;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Dto.standardweight.standardweightmaster.StandardWeightMasterDto;
import com.incede.Model.standardweight.standardweightmaster.StandardWeightMaster;

public interface StandardWeightMasterRepository extends JpaRepository<StandardWeightMaster, Integer>{



	Collection<StandardWeightMaster> findByTenantId(Integer id);

	boolean existsByOrnamentIdAndStandardWeight(Integer ornamentId, BigDecimal standardWeight);

	boolean existsByOrnamentIdAndStandardWeightAndIsDeletedFalse(Integer ornamentId, BigDecimal standardWeight);
	
}
