package bsc.kononov.disksharingwebapp.service;

import bsc.kononov.disksharingwebapp.domain.reference.Reference;
import bsc.kononov.disksharingwebapp.domain.reference.ReferenceValue;
import bsc.kononov.disksharingwebapp.repository.ReferenceValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReferenceValueService {

    private final ReferenceValueRepository referenceValueRepository;

    @Autowired
    public ReferenceValueService(ReferenceValueRepository referenceValueRepository) {
        this.referenceValueRepository = referenceValueRepository;
    }

    public ReferenceValue findByReferenceAndId(Reference reference, Long id) {
        return referenceValueRepository.findByReferenceAndId(reference, id).orElse(null);
    }

}
