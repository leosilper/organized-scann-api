package br.com.fiap.organized_scann_api.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
public class Motorcycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @NotBlank(message = "License plate is mandatory")
    private String licensePlate;

    @NotBlank(message = "RFID is mandatory")
    private String rfid;

    @ManyToOne
    @NotNull(message = "Portal must be provided")
    private Portal portal; 

    @Size(max = 500, message = "Problem description must be at most 500 characters")
    private String problemDescription;


    @NotNull(message = "Entry date is mandatory")
    @PastOrPresent(message = "Entry date must be in the past or present")
    private LocalDate entryDate;

    @NotNull(message = "Availability forecast is mandatory")
    @FutureOrPresent(message = "Availability forecast must be in the present or future")
    private LocalDate availabilityForecast;
}
