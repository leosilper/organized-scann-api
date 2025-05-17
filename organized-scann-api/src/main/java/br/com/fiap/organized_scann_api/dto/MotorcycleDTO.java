package br.com.fiap.organized_scann_api.dto;

import java.time.LocalDate;

import br.com.fiap.organized_scann_api.model.MotorcycleType;
import lombok.Data;

@Data
public class MotorcycleDTO {
    private Long id;
    private String branch;
    private MotorcycleType type; 
    private String licensePlate;
    private String chassis;
    private String rfid;
    private String portalName;
    private String problemDescription;
    private LocalDate entryDate;
    private LocalDate availabilityForecast;
}
