package br.com.fiap.organized_scann_api.service;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.organized_scann_api.model.Moto;
import br.com.fiap.organized_scann_api.model.Portal;
import br.com.fiap.organized_scann_api.repository.MotoRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MotoService {

    private final MotoRepository motoRepository;
    private final PortalRepository portalRepository;

    @Transactional
    @CacheEvict(value = "motos", allEntries = true) // <<< Evita problema do cache
    public Moto save(Moto moto) {
        // Se foi informado um portal, buscar o portal completo no banco
        if (moto.getPortal() != null && moto.getPortal().getId() != null) {
            Portal portal = portalRepository.findById(moto.getPortal().getId())
                .orElseThrow(() -> new RuntimeException("Portal não encontrado com ID: " + moto.getPortal().getId()));
            moto.setPortal(portal);
        }
        return motoRepository.save(moto);
    }

    public Page<Moto> findAll(Pageable pageable) {
        return motoRepository.findAll(pageable);
    }

    public Optional<Moto> findById(Long id) {
        return motoRepository.findById(id);
    }

    @Transactional
    @CacheEvict(value = "motos", allEntries = true) // <<< Evita problema também ao deletar
    public void deleteById(Long id) {
        motoRepository.deleteById(id);
    }

    public Optional<Portal> findPortalById(Long id) {
        return portalRepository.findById(id);
    }
}
