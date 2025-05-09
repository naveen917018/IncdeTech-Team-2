package com.incede.Service.Tax;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.incede.Dto.Tax.TaxMasterDto;
import com.incede.Exception.BusinessException;
import com.incede.Model.Tax.TaxMaster;
import com.incede.Repository.Tax.TaxMasterRepository;

@Service
public class TaxMasterService {

	private final TaxMasterRepository taxMasterRepository;

	public TaxMasterService(TaxMasterRepository taxMasterRepository) {
		this.taxMasterRepository = taxMasterRepository;
	}

	private TaxMasterDto toDto(TaxMaster tax) {
		TaxMasterDto dto = new TaxMasterDto();
		dto.setTaxId(tax.getTaxId());
		dto.setTenantId(tax.getTenantId());
		dto.setTaxName(tax.getTaxName());
		dto.setTaxRate(tax.getTaxRate());
		dto.setGlAccountId(tax.getGlAccountId());
		dto.setIsActive(tax.getIsActive());
		dto.setCreatedBy(tax.getCreatedBy()); 
		dto.setIsDeleted(tax.getIsDeleted());
		return dto;
	}

	private TaxMaster toEntity(TaxMasterDto dto) {
		TaxMaster tax = new TaxMaster();
		tax.setTenantId(dto.getTenantId());
		tax.setTaxName(dto.getTaxName());
		tax.setTaxRate(dto.getTaxRate());
		tax.setGlAccountId(dto.getGlAccountId());
		tax.setIsActive(Boolean.TRUE.equals(dto.getIsActive()));
		tax.setIsDeleted(Boolean.TRUE.equals(dto.getIsDeleted()));
		tax.setCreatedBy(dto.getCreatedBy());
		return tax;
	}

	private void validateTaxRate(BigDecimal rate) {
		if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(new BigDecimal("100.00")) > 0) {
			throw new BusinessException("Tax rate must be between 0.00 and 100.00.");
		}
	}

	private void validateUniqueTaxName(Integer tenantId, String taxName) {
		boolean exists = taxMasterRepository.findByTenantIdAndTaxNameIgnoreCase(tenantId, taxName)
				.filter(t -> !Boolean.TRUE.equals(t.getIsDeleted())).isPresent();
		if (exists) {
			throw new BusinessException("Tax name already exists for this tenant.");
		}
	}

	@Transactional(readOnly = true)
	public List<TaxMasterDto> getAllTaxes() {
		return taxMasterRepository.findAll().stream().filter(t -> !Boolean.TRUE.equals(t.getIsDeleted()))
				.map(this::toDto).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public TaxMasterDto getTaxById(Integer id) {
		TaxMaster tax = taxMasterRepository.findById(id).filter(t -> !Boolean.TRUE.equals(t.getIsDeleted()))
				.orElseThrow(() -> new BusinessException("Tax not found with id: " + id));
		return toDto(tax);
	}

	@Transactional
	public TaxMasterDto createTax(TaxMasterDto dto) {
		validateTaxRate(dto.getTaxRate());
		validateUniqueTaxName(dto.getTenantId(), dto.getTaxName());

		TaxMaster tax = toEntity(dto);
		return toDto(taxMasterRepository.save(tax));
	}

	@Transactional
	public TaxMasterDto updateTax(Integer id, TaxMasterDto dto) {
		validateTaxRate(dto.getTaxRate());

		TaxMaster existing = taxMasterRepository.findById(id).filter(t -> !Boolean.TRUE.equals(t.getIsDeleted()))
				.orElseThrow(() -> new BusinessException("Cannot update. Tax not found with id: " + id));

		if (!existing.getTaxName().equalsIgnoreCase(dto.getTaxName())) {
			validateUniqueTaxName(dto.getTenantId(), dto.getTaxName());
		}

		existing.setTaxName(dto.getTaxName());
		existing.setTaxRate(dto.getTaxRate());
		existing.setGlAccountId(dto.getGlAccountId());
		existing.setIsActive(dto.getIsActive());
		existing.setUpdatedBy(dto.getUpdatedBy());

		return toDto(taxMasterRepository.save(existing));
	}

	@Transactional
	public void deleteTax(Integer id) {
		TaxMaster tax = taxMasterRepository.findById(id).filter(t -> !Boolean.TRUE.equals(t.getIsDeleted()))
				.orElseThrow(() -> new BusinessException("Tax not found with id: " + id));
		tax.setIsDeleted(true);
		taxMasterRepository.save(tax);
	}
}
