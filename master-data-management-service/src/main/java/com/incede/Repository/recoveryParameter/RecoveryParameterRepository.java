package com.incede.Repository.recoveryParameter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.recoveryParameter.RecoveryParameterMaster;

@Repository
public interface RecoveryParameterRepository extends JpaRepository<RecoveryParameterMaster, Integer>{

	Optional<RecoveryParameterMaster> findById(Integer id);

	Optional<RecoveryParameterMaster> findByRecoveryParamId(Integer id);

	boolean existsByTenantIdAndParamNameIgnoreCaseAndIsDeletedFalse(Integer tenantId, String paramName);
	
	Optional<RecoveryParameterMaster> findByRecoveryParamIdAndIsDeletedFalse(Integer recoveryParamId);

	Optional<RecoveryParameterMaster> findAllByIsDeletedFalse();

}
