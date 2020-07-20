package bsc.kononov.disksharingwebapp.service;

import bsc.kononov.disksharingwebapp.domain.item.Disk;
import bsc.kononov.disksharingwebapp.domain.item.TakenItem;
import bsc.kononov.disksharingwebapp.domain.reference.ReferenceValue;
import bsc.kononov.disksharingwebapp.domain.user.User;
import bsc.kononov.disksharingwebapp.exception.ReferenceValueNotFoundException;
import bsc.kononov.disksharingwebapp.exception.UserNotFoundException;
import bsc.kononov.disksharingwebapp.repository.DiskRepository;
import bsc.kononov.disksharingwebapp.repository.ReferenceValueRepository;
import bsc.kononov.disksharingwebapp.repository.TakenItemRepository;
import bsc.kononov.disksharingwebapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static bsc.kononov.disksharingwebapp.service.util.Converter.convertListToMap;

@Service
@Transactional
public class DiskService {

    private static final String USER_NOT_FOUND = "User not found";
    private static final String DISK_NOT_FOUND = "Disk not found";

    private final DiskRepository diskRepository;
    private final UserRepository userRepository;
    private final TakenItemRepository takenItemRepository;

    private final ReferenceValue takeAction;         // Взять
    private final ReferenceValue returnAction;       // Вернуть
    private final ReferenceValue returnDemandAction; // Вернуть по требованию владельца

    @Autowired
    public DiskService(DiskRepository diskRepository,
                       ReferenceValueRepository referenceValueRepository,
                       UserRepository userRepository,
                       TakenItemRepository takenItemRepository) {

        this.diskRepository = diskRepository;
        this.userRepository = userRepository;
        this.takenItemRepository = takenItemRepository;

        takeAction = referenceValueRepository.findById(1L).orElse(null);
        returnAction = referenceValueRepository.findById(2L).orElse(null);
        returnDemandAction = referenceValueRepository.findById(3L).orElse(null);

        if (takeAction == null || returnAction == null || returnDemandAction == null)
            throw new ReferenceValueNotFoundException();
    }

    public Map<String, Object> findOwnDisks() {
        return convertListToMap(diskRepository.findAll(), "owns");
    }

    public Map<String, Object> findFreeDisks() {
        return convertListToMap(diskRepository.findByHolderId(null), "free");
    }

    public Map<String, Object> findTakenByUsername(String authUsername) {
        User authUser = userRepository.findByUsername(authUsername).orElseThrow(UserNotFoundException::new);
        List<TakenItem> items = takenItemRepository.findByToAndOpType(authUser, takeAction);

        Map<String, Object> result = new HashMap<>();
        List<Disk> disks = new ArrayList<>();
        result.put("auth_username", authUser.getUsername());
        result.put("auth_user_id", authUser.getId());
        result.put("taken_items", disks);

        for (TakenItem item: items)
            if (item.getDisk().getHolder() != null)
                disks.add(item.getDisk());

        return result;
    }

    public Map<String, Object> findTakenFromUsername(String username) {
        User from = userRepository.findByUsername(username).orElse(null);

        if (from == null)
            throw new UserNotFoundException();

        List<TakenItem> items = takenItemRepository.findByFromAndOpType(from, takeAction);

        Map<String, Object> result = new HashMap<>();
        List<Object> takenItems = new ArrayList<>();
        result.put("auth_username", username);
        result.put("auth_user_id", from.getId());
        result.put("dropped_items", takenItems);

        for (TakenItem item: items) {
            if (item.getDisk().getHolder() != null) {
                Map<String, Object> diskInfo = new HashMap<>();
                diskInfo.put("disk", item.getDisk());
                diskInfo.put("holder", item.getDisk().getHolder());
                takenItems.add(diskInfo);
            }
        }

        return result;
    }

    public String takeDisk(String authUsername, Long diskId) {
        User authUser = userRepository.findByUsername(authUsername).orElse(null);
        Disk disk = diskRepository.findById(diskId).orElse(null);

        if (authUser == null)
            return USER_NOT_FOUND;

        if (disk == null)
            return DISK_NOT_FOUND;

        boolean authIsOwner = authUser.getId().equals(disk.getOwner().getId());
        if (authIsOwner) {
            if (disk.getHolder() == null) // Владелец не может взять диск у себя
                return "You can't take your own disk from yourself";

            // Владелец может сам забрать свой диск, не дожидаясь возвращения
            return returnDisk(authUsername, disk);
        }

        else if (disk.getHolder() != null) // Нельзя взять диск, т.к. он уже взят
            return "You can't take the disc because it is already taken";

        else { // Диск можно взять
            disk.setHolder(authUser);
            diskRepository.save(disk);
            takenItemRepository.save(new TakenItem(disk, disk.getOwner(), authUser, takeAction, new Date()));

            return "Disk has been taken";
        }
    }

    public String returnDisk(String authUsername, Long diskId) {
        Disk disk = diskRepository.findById(diskId).orElse(null);

        if (disk == null)
            return DISK_NOT_FOUND;

        return returnDisk(authUsername, disk);
    }

    public String returnDisk(String authUsername, Disk disk) {
        if (disk.getHolder() == null) // Владелец не может забрать у себя
            return "You can't return the disc because it is at the owner";

        boolean authIsOwner = disk.getOwner().getUsername().equals(authUsername);
        if (!authIsOwner) {
            if (!authUsername.equals(disk.getHolder().getUsername())) // Нельзя вернуть диск, взятый кем-то другим
                return "You can't return the disc because you did not take it";

            return doReturn(disk, returnAction); // Не владелец может вернуть только тот диск, который он брал
        }

        return doReturn(disk, returnDemandAction); // Владелец может сам забрать свой диск, не дожидаясь возвращения
    }

    private String doReturn(Disk disk, ReferenceValue action) {
        takenItemRepository.save(new TakenItem(disk, disk.getHolder(), disk.getOwner(), action, new Date()));
        disk.setHolder(null);
        diskRepository.save(disk);

        return "Disk has been returned";
    }
}
