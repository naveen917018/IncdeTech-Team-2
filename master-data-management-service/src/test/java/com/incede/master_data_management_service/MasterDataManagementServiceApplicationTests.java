package com.incede.master_data_management_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.incede.Dto.loan.loanproductmaster.LoanProductMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Repository.loan.loanproductmaster.LoanProductMasterRepository;
import com.incede.Service.loan.loanproductmaster.LoanProductMasterService;

@SpringBootTest
class MasterDataManagementServiceApplicationTests {

	@Autowired
	private LoanProductMasterService loanProductMasterService;

	@MockBean
	private LoanProductMasterRepository loanProductMasterRepository;

	@Test
	void testCreateLoanProductMaster_DuplicateName_ShouldThrowException() {
		// Arrange
		LoanProductMasterDto dto = new LoanProductMasterDto();
		dto.setTenantId(1);
		dto.setProductName(" Loan");
		dto.setProductCode("E202");
		dto.setCreatedBy(1);
		dto.setIsActive(true);
		dto.setIsDeleted(false);

		Mockito.when(loanProductMasterRepository.existsByTenantIdAndProductNameIgnoreCase(1, "Educational Loan"))
				.thenReturn(true);
		Mockito.when(loanProductMasterRepository.existsByTenantIdAndProductCode(1, "E202"))
				.thenReturn(true);

		// Act + Assert
		assertThrows(BusinessException.class, () -> {
			loanProductMasterService.createLoanProductMaster(dto);
		});
		
		 BusinessException ex = assertThrows(BusinessException.class, () -> {
		        loanProductMasterService.createLoanProductMaster(dto);
		    });

		    // Optional: check the exception message
		    assertEquals("Cannot pass isDeleted true on creation", ex.getMessage());
	}
}
