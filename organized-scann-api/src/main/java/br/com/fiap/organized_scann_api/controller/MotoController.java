package br.com.fiap.organized_scann_api.controller;

import br.com.fiap.organized_scann_api.dto.MotoDTO;
import br.com.fiap.organized_scann_api.model.Moto;
import br.com.fiap.organized_scann_api.model.Portal;
import br.com.fiap.organized_scann_api.repository.MotoRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import br.com.fiap.organized_scann_api.service.MotoService;
import br.com.fiap.organized_scann_api.specification.MotoSpecification;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/motos")
@RequiredArgsConstructor
public class MotoController {

    private final MotoService motoService;
    private final PortalRepository portalRepository;
    private final MotoRepository motoRepository;

    @PostMapping
    public ResponseEntity<Moto> create(@RequestBody @Valid Moto moto) {
        Moto saved = motoService.save(moto);
        return ResponseEntity.created(URI.create("/api/motos/" + saved.getId())).body(saved);
    }

    @GetMapping
    @Cacheable("motos")
    public Page<MotoDTO> list(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String rfid,
            @RequestParam(required = false) Long portalId,
            Pageable pageable
    ) {
        Portal portal = null;
        if (portalId != null) {
            portal = portalRepository.findById(portalId).orElse(null);
        }

        Specification<Moto> spec = Specification.where(
                MotoSpecification.hasPlaca(placa)
                        .and(MotoSpecification.hasRfid(rfid))
                        .and(MotoSpecification.hasPortal(portal))
        );

        return motoRepository.findAll(spec, pageable)
                .map(this::toDTO); // <<< Usando o novo método para conversão segura
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> get(@PathVariable Long id) {
        return motoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 👇 Novo método de conversão seguro
    private MotoDTO toDTO(Moto moto) {
        MotoDTO dto = new MotoDTO();
        dto.setId(moto.getId()); // Agora também passando o ID!
        dto.setPlaca(moto.getPlaca());
        dto.setRfid(moto.getRfid());
        dto.setPortalNome(moto.getPortal() != null ? moto.getPortal().getNome() : null);
        dto.setPrevisaoDisponibilidade(moto.getPrevisaoDisponibilidade());
        return dto;
    }
}
