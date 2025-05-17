package br.com.fiap.organized_scann_api.dto;

import java.util.List;

import lombok.Data;

@Data
public class PortalSummaryDTO {
    private String portalName;
    private long motorcycleCount;
    private List<String> motorcyclePlates;
    private List<String> motorcycleRfids;   // novo campo RFID
    private List<String> motorcycleChassis; // novo campo chassis
}
