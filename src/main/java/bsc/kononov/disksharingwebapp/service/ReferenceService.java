package bsc.kononov.disksharingwebapp.service;

import bsc.kononov.disksharingwebapp.domain.reference.Reference;
import bsc.kononov.disksharingwebapp.repository.ReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReferenceService {

    private final ReferenceRepository referenceRepository;

    @Autowired
    public ReferenceService(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }

    public Reference findByName(String name) {
        return referenceRepository.findByName(name).orElse(null);
    }

}
