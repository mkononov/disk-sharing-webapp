package bsc.kononov.disksharingwebapp.repository;

import bsc.kononov.disksharingwebapp.domain.item.Disk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiskRepository extends JpaRepository<Disk, Long> {

    List<Disk> findByHolderId(Long takerId);

}
