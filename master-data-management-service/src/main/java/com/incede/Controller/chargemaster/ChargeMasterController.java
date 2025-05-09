package com.incede.Controller.chargemaster;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.incede.Dto.chargemaster.ChargeMasterDto;
import com.incede.Service.chargemaster.ChargeMasterService;

@RestController
@RequestMapping("/v1/masterdata/charges/charge-master")
public class ChargeMasterController {

    private final ChargeMasterService service;

    public ChargeMasterController(ChargeMasterService service) {
        this.service = service;
    }

    // Get all charges
    @GetMapping("/getAll")
    public ResponseEntity<List<ChargeMasterDto>> getAll() {
        return ResponseEntity.ok(service.getAllCharges());
    }

    // Get charge by ID
    @GetMapping("/{id}")
    public ResponseEntity<ChargeMasterDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getChargeById(id));
    }

    // Create a new charge
    @PostMapping("/")
    public ResponseEntity<ChargeMasterDto> create(@RequestBody ChargeMasterDto dto) {
        return ResponseEntity.status(201).body(service.createCharge(dto));
    }

    // Update existing charge
    @PutMapping("/{id}")
    public ResponseEntity<ChargeMasterDto> update(@PathVariable Integer id, @RequestBody ChargeMasterDto dto) {
        return ResponseEntity.ok(service.updateCharge(id, dto));
    }

    // Delete charge (soft delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.deleteCharge(id);
        return ResponseEntity.ok("Charge deleted successfully");
    }
}
