package br.com.fiap.organized_scann_api.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.organized_scann_api.model.Moto;
import br.com.fiap.organized_scann_api.model.Portal;

public class MotoSpecification {

    public static Specification<Moto> hasPlaca(String placa) {
        return (root, query, builder) -> 
            placa == null ? null : builder.like(builder.lower(root.get("placa")), "%" + placa.toLowerCase() + "%");
    }

    public static Specification<Moto> hasRfid(String rfid) {
        return (root, query, builder) -> 
            rfid == null ? null : builder.like(builder.lower(root.get("rfid")), "%" + rfid.toLowerCase() + "%");
    }

    public static Specification<Moto> hasPortal(Portal portal) {
        return (root, query, builder) -> 
            portal == null ? null : builder.equal(root.get("portal"), portal);
    }
}
