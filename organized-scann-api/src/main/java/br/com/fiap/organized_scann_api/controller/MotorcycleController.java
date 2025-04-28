package br.com.fiap.organized_scann_api.controller;

import java.net.URI;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.organized_scann_api.dto.MotorcycleDTO;
import br.com.fiap.organized_scann_api.model.Motorcycle;
import br.com.fiap.organized_scann_api.model.Portal;
import br.com.fiap.organized_scann_api.repository.MotorcycleRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import br.com.fiap.organized_scann_api.service.MotorcycleService;
import br.com.fiap.organized_scann_api.specification.MotorcycleSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/motorcycles")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;
    private final PortalRepository portalRepository;
    private final MotorcycleRepository motorcycleRepository;

    @PostMapping
    public ResponseEntity<Motorcycle> create(@RequestBody @Valid Motorcycle motorcycle) {
        Motorcycle saved = motorcycleService.save(motorcycle);
        return ResponseEntity.created(URI.create("/api/motorcycles/" + saved.getId())).body(saved);
    }

    @GetMapping
    @Cacheable("motorcycles")
    public Page<MotorcycleDTO> list(
            @RequestParam(required = false) String licensePlate,
            @RequestParam(required = false) String rfid,
            @RequestParam(required = false) Long portalId,
            Pageable pageable
    ) {
        Portal portal = null;
        if (portalId != null) {
            portal = portalRepository.findById(portalId).orElse(null);
        }

        Specification<Motorcycle> spec = Specification.where(
                MotorcycleSpecification.hasLicensePlate(licensePlate)
                        .and(MotorcycleSpecification.hasRfid(rfid))
                        .and(MotorcycleSpecification.hasPortal(portal))
        );

        return motorcycleRepository.findAll(spec, pageable)
                .map(this::toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> get(@PathVariable Long id) {
        return motorcycleService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Motorcycle> update(@PathVariable Long id, @RequestBody @Valid Motorcycle updatedMotorcycle) {
        return motorcycleService.findById(id)
                .map(existingMotorcycle -> {
                    existingMotorcycle.setLicensePlate(updatedMotorcycle.getLicensePlate());
                    existingMotorcycle.setRfid(updatedMotorcycle.getRfid());
                    existingMotorcycle.setAvailabilityForecast(updatedMotorcycle.getAvailabilityForecast());
                    existingMotorcycle.setEntryDate(updatedMotorcycle.getEntryDate()); // 🔥 adicionando entryDate
                    existingMotorcycle.setPortal(updatedMotorcycle.getPortal());
                    Motorcycle savedMotorcycle = motorcycleService.save(existingMotorcycle);
                    return ResponseEntity.ok(savedMotorcycle);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motorcycleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MotorcycleDTO toDTO(Motorcycle motorcycle) {
        MotorcycleDTO dto = new MotorcycleDTO();
        dto.setId(motorcycle.getId());
        dto.setLicensePlate(motorcycle.getLicensePlate());
        dto.setRfid(motorcycle.getRfid());
        dto.setPortalName(motorcycle.getPortal() != null ? motorcycle.getPortal().getName() : null);
        dto.setEntryDate(motorcycle.getEntryDate()); // 🔥 adicionando entryDate no DTO
        dto.setAvailabilityForecast(motorcycle.getAvailabilityForecast());
        return dto;
    }
}
