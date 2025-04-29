package br.com.fiap.organized_scann_api.config;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.organized_scann_api.model.Motorcycle;
import br.com.fiap.organized_scann_api.model.Portal;
import br.com.fiap.organized_scann_api.model.PortalType;
import br.com.fiap.organized_scann_api.model.User;
import br.com.fiap.organized_scann_api.model.UserRole;
import br.com.fiap.organized_scann_api.repository.MotorcycleRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import br.com.fiap.organized_scann_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final PortalRepository portalRepository;
    private final MotorcycleRepository motorcycleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        // Criar usuários se não existirem
        if (userRepository.count() == 0) {
            userRepository.saveAll(List.of(
                User.builder()
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(UserRole.ADMIN)
                    .build(),

                User.builder()
                    .email("supervisor@example.com")
                    .password(passwordEncoder.encode("supervisor123"))
                    .role(UserRole.SUPERVISOR)
                    .build(),

                User.builder()
                    .email("operator@example.com")
                    .password(passwordEncoder.encode("operator123"))
                    .role(UserRole.OPERATOR)
                    .build()
            ));
        }

        // Criar portais e motos se necessário
        if (portalRepository.count() == 0) {
            var quickMaintenance = portalRepository.save(new Portal(null, PortalType.QUICK_MAINTENANCE, "Quick Maintenance"));
            var slowMaintenance = portalRepository.save(new Portal(null, PortalType.SLOW_MAINTENANCE, "Slow Maintenance"));
            var policeReport = portalRepository.save(new Portal(null, PortalType.POLICE_REPORT, "Police Report"));
            var recoveredMotorcycle = portalRepository.save(new Portal(null, PortalType.RECOVERED_MOTORCYCLE, "Recovered Motorcycles"));

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
                        .problemDescription(problems.get(i))
                        .portal(portals.get(new Random().nextInt(portals.size())))
                        .entryDate(LocalDate.now().minusDays(new Random().nextInt(10)))
                        .availabilityForecast(LocalDate.now().plusDays(new Random().nextInt(20) + 1))
                        .build());
            }
        }
    }
}
