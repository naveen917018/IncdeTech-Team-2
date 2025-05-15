package com.incede.Repository.liability.liabilityglheadconfiguration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Enum.GLAccountType;
import com.incede.Model.liability.liabilityglheadconfiguration.LiabilitySchemeGlHeadConfiguration;

@Repository
public interface LiabilitySchemeGlHeadConfigurationRepository
		extends JpaRepository<LiabilitySchemeGlHeadConfiguration, Integer> {

	boolean existsByGlAccountTypeAndSchemeId(GLAccountType glAccountType, Integer schemeId);

	boolean existsByGlAccountTypeAndSchemeIdAndIsDeletedFalse(GLAccountType glAccountType, Integer schemeId);

}
