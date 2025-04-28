package br.com.fiap.organized_scann_api.config;

import br.com.fiap.organized_scann_api.model.Moto;
import br.com.fiap.organized_scann_api.model.Portal;
import br.com.fiap.organized_scann_api.model.PortalTipo;
import br.com.fiap.organized_scann_api.repository.MotoRepository;
import br.com.fiap.organized_scann_api.repository.PortalRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {

    private final PortalRepository portalRepository;
    private final MotoRepository motoRepository;

    @PostConstruct
    public void init() {
        if (portalRepository.count() == 0) {
            // Criando Portais
            var manutencaoRapida = portalRepository.save(new Portal(null, PortalTipo.MANUTENCAO_RAPIDA, "Manutenção rápida"));
            var manutencaoDemorada = portalRepository.save(new Portal(null, PortalTipo.MANUTENCAO_DEMORADA, "Manutenção demorada"));
            var boletimOcorrencia = portalRepository.save(new Portal(null, PortalTipo.BOLETIM_OCORRENCIA, "Boletins de ocorrência"));
            var motoRecuperada = portalRepository.save(new Portal(null, PortalTipo.MOTO_RECUPERADA, "Motos recuperadas / carcaças"));

            // Criando Motos de exemplo
            var placas = List.of("ABC1D23", "XYZ9K88", "DEF4G56", "GHI7J89", "LMN5P10");
            var rfids = List.of("RFID123", "RFID456", "RFID789", "RFID321", "RFID654");
            var portais = List.of(manutencaoRapida, manutencaoDemorada, boletimOcorrencia, motoRecuperada);

            for (int i = 0; i < placas.size(); i++) {
                motoRepository.save(Moto.builder()
                        .placa(placas.get(i))
                        .rfid(rfids.get(i))
                        .previsaoDisponibilidade(LocalDate.now().plusDays(new Random().nextInt(15) + 1))
                        .portal(portais.get(new Random().nextInt(portais.size())))
                        .build());
            }
        }
    }
}
