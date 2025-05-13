package com.incede.Controller.recoveryParameter;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.recoveryParameter.RecoveryParameterMasterDTO;
import com.incede.Service.recoveryParameter.RecoveryParameterMasterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/masterdata/recoveryparameter-master")
public class RecoveryParameterController {
	
	private final RecoveryParameterMasterService service;
	
	public RecoveryParameterController(RecoveryParameterMasterService service) {
		// TODO Auto-generated constructor stub
		this.service = service;
	}

//    @PostMapping
//    public ResponseEntity<RecoveryParameterMasterDTO> create(@Valid @RequestBody RecoveryParameterMasterDTO dto) {
//        return ResponseEntity.ok(service.createParameter(dto));
//    }
	
	@PostMapping
    public ResponseEntity<RecoveryParameterMasterDTO> createParameter(
            @Valid @RequestBody RecoveryParameterMasterDTO dto) {
		System.out.println(dto);
        RecoveryParameterMasterDTO result = service.createParameter(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecoveryParameterMasterDTO> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getParameterById(id));
    }

    @GetMapping
    public ResponseEntity<List<RecoveryParameterMasterDTO>> getAll() {
        return ResponseEntity.ok(service.getAllParameters());
    }

    @PutMapping
    public ResponseEntity<RecoveryParameterMasterDTO> update( @RequestBody RecoveryParameterMasterDTO dto) {
        return ResponseEntity.ok(service.updateParameter(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.deleteParameter(id);
        return ResponseEntity.noContent().build();
    }

}
