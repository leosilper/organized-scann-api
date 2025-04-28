package br.com.fiap.organized_scann_api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.organized_scann_api.dto.PortalResumoDTO;
import br.com.fiap.organized_scann_api.repository.MotoRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/portais")
@RequiredArgsConstructor
public class PortalController {

    private final PortalRepository portalRepository;
    private final MotoRepository motoRepository;

    @GetMapping("/resumo")
    public List<PortalResumoDTO> listarResumo() {
        return portalRepository.findAll().stream().map(portal -> {
            PortalResumoDTO dto = new PortalResumoDTO();
            dto.setNomePortal(portal.getNome());
            var motos = motoRepository.findAll().stream()
                    .filter(m -> m.getPortal().getId().equals(portal.getId()))
                    .collect(Collectors.toList());
            dto.setQuantidadeMotos(motos.size());
            dto.setPlacasMotos(motos.stream().map(m -> m.getPlaca()).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }
}
