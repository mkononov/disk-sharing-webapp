package bsc.kononov.disksharingwebapp.repository;

import bsc.kononov.disksharingwebapp.domain.reference.Reference;
import bsc.kononov.disksharingwebapp.domain.reference.ReferenceValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferenceValueRepository extends JpaRepository<ReferenceValue, Long> {

    Optional<ReferenceValue> findByReferenceAndId(Reference reference, Long id);

}
