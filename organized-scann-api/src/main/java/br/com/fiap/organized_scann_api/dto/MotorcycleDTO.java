package br.com.fiap.organized_scann_api.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MotorcycleDTO {
    private Long id;
    private String licensePlate;
    private String rfid;
    private String portalName;
    private String problemDescription; // 🛠️ Descrição do problema posicionada abaixo do portal
    private LocalDate entryDate;        // 🔥 Data de entrada
    private LocalDate availabilityForecast;
}
