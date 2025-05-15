package com.incede.Repository.liability.liabilityProduct;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.liability.liabilityProductMaster.LiabilityProductMaster;


@Repository
public interface LiabilityProductRepository  extends JpaRepository<LiabilityProductMaster, Integer> {

	

    LiabilityProductMaster findByTenantIdAndProductCode(Integer tenantId, String productCode);

    boolean existsByTenantIdAndProductCode(Integer tenantId, String productCode);
    
    List<LiabilityProductMaster> findByTenantId(Integer tenantId); // <-- Add this line

    List<LiabilityProductMaster> findByProductCode(String productCode); // <-- Add this

	boolean existsByTenantIdAndProductId(Integer tenantId, Integer productId);


}
