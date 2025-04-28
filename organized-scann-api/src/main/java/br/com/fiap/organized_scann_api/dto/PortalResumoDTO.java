package br.com.fiap.organized_scann_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class PortalResumoDTO {
    private String nomePortal;
    private long quantidadeMotos;
    private List<String> placasMotos;
}
