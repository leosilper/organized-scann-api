package br.com.fiap.organized_scann_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import br.com.fiap.organized_scann_api.model.Moto;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long>, JpaSpecificationExecutor<Moto> {}
