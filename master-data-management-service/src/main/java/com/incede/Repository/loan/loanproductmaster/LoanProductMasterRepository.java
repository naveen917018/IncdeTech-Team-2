package com.incede.Repository.loan.loanproductmaster;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.incede.Dto.loan.loanproductmaster.LoanProductMasterDto;
import com.incede.Model.loan.loanproductmaster.LoanProductMaster;

public interface LoanProductMasterRepository extends JpaRepository<LoanProductMaster, Integer>{

	boolean existsByTenantIdAndProductNameIgnoreCase(Integer tenantId, String productName);

	boolean existsByTenantIdAndProductCode(Integer tenantId, String productCode);

	Collection<LoanProductMaster> findByTenantId(Integer tenantId);

	List<LoanProductMaster> findByLoanCategoryId(Integer loanCategoryId);


}
