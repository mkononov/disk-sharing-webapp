package bsc.kononov.disksharingwebapp.repository;

import bsc.kononov.disksharingwebapp.domain.reference.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    Optional<Reference> findByName(String name);

}
