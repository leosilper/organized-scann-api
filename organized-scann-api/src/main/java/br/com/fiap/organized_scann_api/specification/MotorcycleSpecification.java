package br.com.fiap.organized_scann_api.specification;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.organized_scann_api.model.Motorcycle;
import br.com.fiap.organized_scann_api.model.Portal;

public class MotorcycleSpecification {

    public static Specification<Motorcycle> hasLicensePlate(String licensePlate) {
        return (root, query, builder) -> 
            licensePlate == null ? null : builder.like(builder.lower(root.get("licensePlate")), "%" + licensePlate.toLowerCase() + "%");
    }

    public static Specification<Motorcycle> hasRfid(String rfid) {
        return (root, query, builder) -> 
            rfid == null ? null : builder.like(builder.lower(root.get("rfid")), "%" + rfid.toLowerCase() + "%");
    }

    public static Specification<Motorcycle> hasPortal(Portal portal) {
        return (root, query, builder) -> 
            portal == null ? null : builder.equal(root.get("portal"), portal);
    }

    public static Specification<Motorcycle> hasType(String type) {
        return (root, query, builder) -> 
            type == null ? null : builder.like(builder.lower(root.get("type")), "%" + type.toLowerCase() + "%");
    }

    public static Specification<Motorcycle> hasBranch(String branch) {
        return (root, query, builder) -> 
            branch == null ? null : builder.like(builder.lower(root.get("branch")), "%" + branch.toLowerCase() + "%");
    }

    public static Specification<Motorcycle> hasChassis(String chassis) {
        return (root, query, builder) -> 
            chassis == null ? null : builder.like(builder.lower(root.get("chassis")), "%" + chassis.toLowerCase() + "%");
    }
}
