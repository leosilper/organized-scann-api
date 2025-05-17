package br.com.fiap.organized_scann_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.organized_scann_api.dto.PortalSummaryDTO;
import br.com.fiap.organized_scann_api.repository.MotorcycleRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portals")
@RequiredArgsConstructor
public class PortalController {

    private final PortalRepository portalRepository;
    private final MotorcycleRepository motorcycleRepository;

    @GetMapping("/summary")
    public List<PortalSummaryDTO> listSummary() {
        return portalRepository.findAll().stream().map(portal -> {
            PortalSummaryDTO dto = new PortalSummaryDTO();
            dto.setPortalName(portal.getName());

            var motorcycles = motorcycleRepository.findAll().stream()
                .filter(m -> m.getPortal() != null && m.getPortal().getId().equals(portal.getId()))
                .collect(Collectors.toList());

            dto.setMotorcycleCount(motorcycles.size());
            dto.setMotorcyclePlates(motorcycles.stream()
                .map(m -> m.getLicensePlate())
                .collect(Collectors.toList()));
            dto.setMotorcycleRfids(motorcycles.stream()
                .map(m -> m.getRfid())
                .collect(Collectors.toList()));
            dto.setMotorcycleChassis(motorcycles.stream()
                .map(m -> m.getChassis())
                .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }
}
