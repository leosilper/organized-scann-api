package br.com.fiap.organized_scann_api.controller;

import java.net.URI;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String branch,    // Novo filtro branch
            @RequestParam(required = false) String chassis,   // Novo filtro chassis
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
                    .and(MotorcycleSpecification.hasType(type))
                    .and(MotorcycleSpecification.hasBranch(branch))
                    .and(MotorcycleSpecification.hasChassis(chassis))
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
                    existingMotorcycle.setEntryDate(updatedMotorcycle.getEntryDate());
                    existingMotorcycle.setAvailabilityForecast(updatedMotorcycle.getAvailabilityForecast());
                    existingMotorcycle.setPortal(updatedMotorcycle.getPortal());
                    existingMotorcycle.setProblemDescription(updatedMotorcycle.getProblemDescription());
                    existingMotorcycle.setType(updatedMotorcycle.getType());
                    existingMotorcycle.setBranch(updatedMotorcycle.getBranch());
                    existingMotorcycle.setChassis(updatedMotorcycle.getChassis());
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
        dto.setBranch(motorcycle.getBranch());
        dto.setType(motorcycle.getType());
        dto.setLicensePlate(motorcycle.getLicensePlate());
        dto.setChassis(motorcycle.getChassis());
        dto.setRfid(motorcycle.getRfid());
        dto.setPortalName(motorcycle.getPortal() != null ? motorcycle.getPortal().getName() : null);
        dto.setProblemDescription(motorcycle.getProblemDescription());
        dto.setEntryDate(motorcycle.getEntryDate());
        dto.setAvailabilityForecast(motorcycle.getAvailabilityForecast());
        return dto;
    }
}
