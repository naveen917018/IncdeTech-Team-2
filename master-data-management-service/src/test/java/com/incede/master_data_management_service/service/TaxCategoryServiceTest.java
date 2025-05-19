package com.incede.master_data_management_service.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.incede.Dto.taxCategory.TaxCategoryDto;
import com.incede.Model.taxCategory.TaxCategory;
import com.incede.Repository.taxCategory.TaxCategoryRepository;
import com.incede.Service.taxCategory.TaxCategoryService;

@ExtendWith(MockitoExtension.class)
public class TaxCategoryServiceTest {
	
	@Mock
	TaxCategoryRepository repo;
	
	@InjectMocks
	TaxCategoryService taxCategoryService;
	
	@Test
	void myFirstTest() {
		System.out.println("My first Test Method");
	}
	
	@Test
	void createTaxCategoryTest() {
		System.out.println("Creating Tac Category is successfull");
		TaxCategoryDto taxDto = new TaxCategoryDto();
		taxDto.setName("GST 10%");
		taxDto.setTenantId(100);
		taxDto.setCreatedBy(10);
		
		TaxCategory mockEntity = new TaxCategory();
		
	    mockEntity.setTaxCategoryId(1);
	    mockEntity.setName("GST 10%");
	    mockEntity.setTenantId(100);
	    mockEntity.setCreatedBy(10);
	    when(repo.findByTenantIdAndNameIgnoreCase(100,"GST 10%")).thenReturn(Optional.of(mockEntity));
	    when(repo.save(any())).thenReturn(mockEntity);
		System.out.println(1);
		TaxCategoryDto tax = taxCategoryService.create(taxDto);
		System.out.println(2);
		System.out.println("Mock Tax"+tax);
	}

}
