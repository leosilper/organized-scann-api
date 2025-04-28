package br.com.fiap.organized_scann_api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MotoDTO {
    private Long id;
    private String placa;
    private String rfid;
    private String portalNome;
    private LocalDate previsaoDisponibilidade;
}
