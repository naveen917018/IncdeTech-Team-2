package com.incede.Service.liability.liabilityProduct;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.hibernate.annotations.TenantId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.customer.customerStatus.CustomerStatusDto;
import com.incede.Dto.liability.liabilityProduct.LiabilityProductDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.customer.customerStatus.CustomerStatus;
import com.incede.Model.liability.liabilityProductMaster.LiabilityProductMaster;
import com.incede.Repository.liability.liabilityProduct.LiabilityProductRepository;


@Service
public class LiabilityProductService{

	 
	  private final LiabilityProductRepository liabilityproductrepository;

	    public LiabilityProductService(LiabilityProductRepository liabilityproductrepository) {
	        this.liabilityproductrepository = liabilityproductrepository;
	    }
	    
	    
	        
	    
	    
	    private LiabilityProductDto toDto(LiabilityProductMaster lpm) {
	    	LiabilityProductDto dto = new LiabilityProductDto();
	    		 dto.setProductId(lpm.getProductId());
	             dto.setTenantId(lpm.getTenantId());  
	             dto.setProductName(lpm.getProductName());
	             dto.setProductCode(lpm.getProductCode());
	             dto.setProductDescription(lpm.getProductDescription());
	             dto.setIsActive(lpm.getIsActive());
	             
	     		dto.setIsDeleted(lpm.getIsDeleted());
	     		dto.setCreatedBy(lpm.getCreatedBy());
	     		dto.setUpdatedBy(lpm.getUpdatedBy());
	             return dto;
	    }

	    private LiabilityProductMaster toEntity(LiabilityProductDto dto) {
	        LiabilityProductMaster entity = new LiabilityProductMaster();
	        
	        entity.setTenantId(dto.getTenantId());  
	        entity.setProductName(dto.getProductName());
	        entity.setProductCode(dto.getProductCode());
	        entity.setProductDescription(dto.getProductDescription());
	        	        
	        entity.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
			entity.setIsDeleted(dto.getIsDeleted() != null ? dto.getIsDeleted() : false);

			if (dto.getCreatedBy() != null) {
				entity.setCreatedBy(dto.getCreatedBy());
			}

			return entity;
	        	                
	    }
	    
//----------------------------------------CREATE LIABILITY PRODUCT-----------------------------------

	    @Transactional
	    public LiabilityProductDto createLiabilityProduct(LiabilityProductDto dto) {
	    	
	    	if (dto == null) {
	            throw new BusinessException("Request body cannot be null.");
	        }

	    	
	    	
			//	        if (dto.getProductCode() == null || dto.getProductCode().trim().isEmpty()) {
//	            throw new BusinessException("Product code is required.");
//	        }
//
//	        if (dto.getTenantId() == null) {
//	            throw new BusinessException("Tenant ID is required.");
//	        }
//
//	        if (dto.getCreatedBy() == null) {
//	            throw new BusinessException("CreatedBy is required.");
//	        }
//	        
//	        if (dto.getProductName() == null) {
//	            throw new BusinessException("product Name is required.");
//	        }
//
//	        if (dto.getProductDescription() == null) {
//	            throw new BusinessException("product description is required.");
//	        }
//
//	        if(dto.getUpdatedBy().intValue() != dto.getCreatedBy().intValue()) {
//	        	throw new BusinessException("Updated id should be same as Created by or should be null");
//	        	
//	        }
//	        if (dto.getIsActive() == null) {
//	            throw new BusinessException("isActive field is required and must be a boolean value.");
//	        }
//
//	        if (dto.getIsDeleted() == null) {
//	            throw new BusinessException("isDeleted field is required and must be a boolean value.");
//	        }
	    	

	    	if(Boolean.FALSE.equals(dto.getIsActive())) {
	    		throw new BusinessException("Cannot pass IsActive is FALSE on creation ");
	    	}
	    	
	    
	    	
	    	if(Boolean.TRUE.equals(dto.getIsDeleted())) {
	    		throw new BusinessException("Cannot pass Isdelete is TRUE on creation");
	    	}
	    	
	    
	    	
	    	
			// Check for existing product ID within the same tenant
			if (dto.getProductId() != null) {
					boolean productIdExists = liabilityproductrepository.existsByTenantIdAndProductId(dto.getTenantId(), dto.getProductId());
					if (productIdExists) {
						throw new BusinessException("The 'productId' already exists for this tenant.");
					}
			}

	        
			//Check for existing product code within the same tenant

	        boolean exists = liabilityproductrepository.existsByTenantIdAndProductCode(dto.getTenantId(), dto.getProductCode());
	        if (exists) {
	            throw new BusinessException("Product code already exists for this tenant.");
	        }
	        
	        
	        
	        LiabilityProductMaster saved = toEntity(dto);
	        liabilityproductrepository.save(saved);
	        return toDto(saved);
	    }

	    
	    
//-------------------------------------------------GET ALL PRODUCTS----------------------------------------------
	    
	    
	    @Transactional(readOnly = true)
	    public List<LiabilityProductDto> getAllLiabilityProducts() {
	        try {
	            // Retrieve all products from the database
	            List<LiabilityProductMaster> products = liabilityproductrepository.findAll();

	            // Check if the products list is null or empty
	            if (products == null || products.isEmpty()) {
	                throw new BusinessException("No liability products found in the database.");
	            }

	            // Manually filter the products based on the business rules
	            List<LiabilityProductMaster> filteredProducts = products.stream()
	                .filter(product -> product.getIsActive() != null && product.getIsActive()  // isActive is true
	                    && (product.getIsDeleted() == null || !product.getIsDeleted()) // isDeleted is false or null
	                    && product.getTenantId() != null  // tenantId is not null
	                    && product.getProductCode() != null) // productCode is not null
	                .collect(Collectors.toList());

	            // If no products meet the criteria, throw an exception
	            if (filteredProducts.isEmpty()) {
	                throw new BusinessException("No valid liability products found with active status and not deleted.");
	            }

	            // Convert the filtered products to DTOs using stream and return the result
	            return filteredProducts.stream()
	                    .map(this::toDto)
	                    .collect(Collectors.toList());

	        } catch (Exception e) {
	            // Log the exception and throw a custom exception with the error message
	            throw new BusinessException("An error occurred while fetching liability products: " + e.getMessage());
	        }
	    }


	    
//-------------------------------------UPDATE PRODUCT---------------------------------------------
	    
	    @Transactional
	    public LiabilityProductDto updateLiabilityProduct(Integer id, LiabilityProductDto dto) {
	    	
	    	 if (id == null) {
	    	        throw new BusinessException("Product ID cannot be null.");
	    	    }

	    	    if (dto == null) {
	    	        throw new BusinessException("Request body cannot be null.");
	    	    }

	    	    if (dto.getUpdatedBy() == null) {
	    	        throw new BusinessException("UpdatedBy is required.");
	    	    }
	    	
	        LiabilityProductMaster existing = liabilityproductrepository.findById(id)
	                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));

	        if (!existing.getProductCode().equalsIgnoreCase(dto.getProductCode())) {
	            boolean exists = liabilityproductrepository.existsByTenantIdAndProductCode(dto.getTenantId(), dto.getProductCode());
	            if (exists) {
	                throw new BusinessException("Product code already exists for this tenant.");
	            }
	        }
	        
	        
	        
	
	        existing.setProductName(dto.getProductName());
	        existing.setProductCode(dto.getProductCode());
	        existing.setTenantId(dto.getTenantId());
	        existing.setProductDescription(dto.getProductDescription());
	        existing.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);

	        return toDto(liabilityproductrepository.save(existing));
	    }

//---------------------------------------------DELETE PRODUCT---------------------------------------
	    
	    @Transactional
	    public void deleteProduct(Integer id) {

	        if (id == null) {
	            throw new BusinessException("Product ID cannot be null.");
	        }
	    	
	        LiabilityProductMaster product = liabilityproductrepository.findById(id)
	                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));

//	        product.setIsActive(false); // soft delete
	        product.setIsDeleted(true);

	        liabilityproductrepository.save(product);
	        
	        
	    }


//------------------------------------------ GET BY PRODUCT ID-----------------------------------------

	    @Transactional(readOnly = true)
	    public LiabilityProductMaster getLiabilityProductById(Integer id) {
	    	

	        if (id == null) {
	            throw new BusinessException("Product ID cannot be null.");
	        }
	    	
	    	
	        return liabilityproductrepository.findById(id)
	                .filter(product -> product.getIsDeleted() == null || !product.getIsDeleted())
	                .orElseThrow(() -> new BusinessException("Product not found with id: " + id));
	        
	        
	    	
	        
	    }



//--------------------------------------------GET ALL BY TENANT ID--------------------------------------------
	    
	    @Transactional(readOnly = true)
	    public List<LiabilityProductDto> getProductsByTenantId(Integer tenantId) {
	        try {
	            List<LiabilityProductMaster> products = liabilityproductrepository.findByTenantId(tenantId);

	            if (products == null || products.isEmpty()) {
	                throw new BusinessException("No products found for tenantId: " + tenantId);
	            }

	            List<LiabilityProductMaster> filteredProducts = products.stream()
	                .filter(product -> product.getIsActive() != null && product.getIsActive()
	                    && (product.getIsDeleted() == null || !product.getIsDeleted())
	                    && product.getProductCode() != null)
	                .collect(Collectors.toList());

	            if (filteredProducts.isEmpty()) {
	                throw new BusinessException("No valid active products found for tenantId: " + tenantId);
	            }

	            return filteredProducts.stream()
	                    .map(this::toDto)
	                    .collect(Collectors.toList());

	        } catch (Exception e) {
	            throw new BusinessException("Error fetching products for tenantId: " + tenantId);
	        }
	    }



//-----------------------------------------------------GET PRODUCTS BY PRODUCT CODE-------------------------------------

		public List<LiabilityProductDto> getProductsByProductCode(String productCode) {
			 try {
				 List<LiabilityProductMaster> products = liabilityproductrepository.findByProductCode(productCode);

				    if (products == null || products.isEmpty()) {
				        throw new BusinessException("No products found for the given product code: " + productCode);
				    }

				    return products.stream()
				        .filter(product -> product.getIsActive() != null && product.getIsActive()
				            && (product.getIsDeleted() == null || !product.getIsDeleted()))
				        .map(this::toDto)
				        .collect(Collectors.toList());
				}catch (Exception e) {
		            throw new BusinessException("Error fetching products for productCode: " + productCode);
		        }
		    }


}
