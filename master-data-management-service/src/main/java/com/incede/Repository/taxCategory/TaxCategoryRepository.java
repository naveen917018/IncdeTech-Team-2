package com.incede.Repository.taxCategory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.taxCategory.TaxCategory;

@Repository
public interface TaxCategoryRepository extends JpaRepository<TaxCategory, Integer> {

//	Optional<TaxCategory> findByTenantIdAndTaxCategoryId(Integer tenantId, Integer taxCatId);
	Optional<TaxCategory> findByTenantIdAndTaxCategoryIdAndIsDeletedFalse(Integer tenantId, Integer taxCatId);


	List<TaxCategory> findAllByTenantIdAndIsDeletedFalse(Integer tenantId);

//	boolean existsByTenantIdAndName(Integer tenantId, String name); 
	boolean existsByTenantIdAndNameIgnoreCaseAndTaxCategoryIdNotAndIsDeletedFalse(
	        Integer tenantId,
	        String name,
	        Integer categoryId
	    );
	
	boolean existsByTenantIdAndNameIgnoreCaseAndIsDeletedFalse(
	        Integer tenantId,
	        String name
	    );

}
