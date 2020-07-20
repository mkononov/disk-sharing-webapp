package bsc.kononov.disksharingwebapp.repository;

import bsc.kononov.disksharingwebapp.domain.item.TakenItem;
import bsc.kononov.disksharingwebapp.domain.reference.ReferenceValue;
import bsc.kononov.disksharingwebapp.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TakenItemRepository extends JpaRepository<TakenItem, Long> {

    @Query(
            value = "SELECT takenItems " +
                        "FROM TakenItem takenItems WHERE takenItems.id IN " +
                            "(SELECT MAX(takenItems.id) " +
                                "FROM TakenItem takenItems WHERE takenItems.to = ?1 AND takenItems.opType = ?2 " +
                                "GROUP BY takenItems.disk)"
    )
    List<TakenItem> findByToAndOpType(User taker, ReferenceValue opType);

    @Query(
            value = "SELECT takenItems " +
                        "FROM TakenItem takenItems WHERE takenItems.id IN " +
                            "(SELECT MAX(takenItems.id) " +
                                "FROM TakenItem takenItems WHERE takenItems.from = ?1 AND takenItems.opType = ?2 " +
                                "GROUP BY takenItems.disk)"
    )
    List<TakenItem> findByFromAndOpType(User dropper, ReferenceValue opType);

}
