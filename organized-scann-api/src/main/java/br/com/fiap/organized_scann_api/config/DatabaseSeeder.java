package br.com.fiap.organized_scann_api.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

import br.com.fiap.organized_scann_api.model.Motorcycle;
import br.com.fiap.organized_scann_api.model.Portal;
import br.com.fiap.organized_scann_api.model.PortalType;
import br.com.fiap.organized_scann_api.repository.MotorcycleRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final PortalRepository portalRepository;
    private final MotorcycleRepository motorcycleRepository;

    @PostConstruct
    public void init() {
        if (portalRepository.count() == 0) {
            // Create Portals
            var quickMaintenance = portalRepository.save(new Portal(null, PortalType.QUICK_MAINTENANCE, "Quick Maintenance"));
            var slowMaintenance = portalRepository.save(new Portal(null, PortalType.SLOW_MAINTENANCE, "Slow Maintenance"));
            var policeReport = portalRepository.save(new Portal(null, PortalType.POLICE_REPORT, "Police Report"));
            var recoveredMotorcycle = portalRepository.save(new Portal(null, PortalType.RECOVERED_MOTORCYCLE, "Recovered Motorcycles"));

            // Example values
            var plates = List.of("ABC1D23", "XYZ9K88", "DEF4G56", "GHI7J89", "LMN5P10");
            var rfids = List.of("RFID123", "RFID456", "RFID789", "RFID321", "RFID654");
            var problems = List.of(
                "Flat tire", 
                "Electrical issue", 
                "Stolen and recovered", 
                "Engine overheating", 
                "Routine maintenance"
            );
            var portals = List.of(quickMaintenance, slowMaintenance, policeReport, recoveredMotorcycle);

            for (int i = 0; i < plates.size(); i++) {
                motorcycleRepository.save(Motorcycle.builder()
                        .licensePlate(plates.get(i))
                        .rfid(rfids.get(i))
                        .problemDescription(problems.get(i)) // ✅ Adicionado
                        .portal(portals.get(new Random().nextInt(portals.size())))
                        .entryDate(LocalDate.now().minusDays(new Random().nextInt(10)))
                        .availabilityForecast(LocalDate.now().plusDays(new Random().nextInt(20) + 1))
                        .build());
            }
        }
    }
}
