package com.incede.Controller.notificationPreference;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.incede.Dto.notificationPreference.NotificationPreferenceDto;
import com.incede.Service.notificationPreference.NotificationPreferenceService;

@RestController
@RequestMapping("/v1/masterdata/notificationpreferences")
public class NotificationPreferenceController {
	
	private final NotificationPreferenceService notificationPreferenceService;

    public NotificationPreferenceController(NotificationPreferenceService service) {
        this.notificationPreferenceService = service;
    }

    @GetMapping
    public ResponseEntity<List<NotificationPreferenceDto>> getAll() {
        return ResponseEntity.ok(notificationPreferenceService.getAll());
    }
    
    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<NotificationPreferenceDto>> getByTenantId(
            @PathVariable Integer tenantId) {
        List<NotificationPreferenceDto> dtos = notificationPreferenceService.getByTenantId(tenantId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationPreferenceDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationPreferenceService.getById(id));
    }

    @PostMapping
    public ResponseEntity<NotificationPreferenceDto> createNotificationPreference(@RequestBody NotificationPreferenceDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationPreferenceService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationPreferenceDto> updateNotificationPreference(@PathVariable Integer id, @RequestBody NotificationPreferenceDto dto) {
        return ResponseEntity.ok(notificationPreferenceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationPreference(@PathVariable Integer id) {
    	notificationPreferenceService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
