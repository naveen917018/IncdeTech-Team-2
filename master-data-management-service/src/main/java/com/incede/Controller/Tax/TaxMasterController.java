package com.incede.Controller.Tax;

import com.incede.Dto.Tax.TaxMasterDto;
import com.incede.Service.Tax.TaxMasterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/masterdata/tax/tax-master")
public class TaxMasterController {

    private final TaxMasterService taxMasterService;

    public TaxMasterController(TaxMasterService taxMasterService) {
        this.taxMasterService = taxMasterService;
    }

    // ✅ Create Tax
    @PostMapping("/")
    public ResponseEntity<TaxMasterDto> createTax(@RequestBody TaxMasterDto dto) {
        return ResponseEntity.ok(taxMasterService.createTax(dto));
    }

    // ✅ Get All (Active + Not Deleted)
    @GetMapping("/getAll")
    public ResponseEntity<List<TaxMasterDto>> getAllTaxes() {
        return ResponseEntity.ok(taxMasterService.getAllTaxes());
    }

    // ✅ Get Tax by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaxMasterDto> getTaxById(@PathVariable Integer id) {
        return ResponseEntity.ok(taxMasterService.getTaxById(id));
    }

    // ✅ Update Tax
    @PutMapping("/{id}")
    public ResponseEntity<TaxMasterDto> updateTax(@PathVariable Integer id, @RequestBody TaxMasterDto dto) {
        return ResponseEntity.ok(taxMasterService.updateTax(id, dto));
    }
    @DeleteMapping("/{taxId}")
    public ResponseEntity<String> deleteTax(@PathVariable Integer taxId) {
        taxMasterService.deleteTax(taxId);
        return ResponseEntity.ok("Tax deleted successfully");
    }
}
