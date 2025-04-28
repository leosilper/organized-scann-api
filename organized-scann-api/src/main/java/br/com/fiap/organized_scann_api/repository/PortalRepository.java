package br.com.fiap.organized_scann_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.organized_scann_api.model.Portal;

@Repository
public interface PortalRepository extends JpaRepository<Portal, Long> {}
