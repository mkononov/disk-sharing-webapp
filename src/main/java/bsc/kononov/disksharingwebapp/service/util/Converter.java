package bsc.kononov.disksharingwebapp.service.util;

import bsc.kononov.disksharingwebapp.domain.item.Disk;

import java.util.*;


public class Converter {

    private Converter() {
    }

    public static Map<String, Object> convertListToMap(List<Disk> disks, String root) {
        Map<String, Object> preResult = new HashMap<>();

        for (Disk disk: disks) {
            String username = disk.getOwner().getUsername();

            if (preResult.containsKey(username)) {
                Map<String, Object> userData = (Map)preResult.get(username);
                List<Disk> items = (List)userData.get("disks");
                items.add(disk);
            } else {
                Map<String, Object> userData = new HashMap<>();
                List<Disk> items = new ArrayList<>();
                userData.put("user_id", disk.getOwner().getId());
                userData.put("disks", items);
                items.add(disk);
                preResult.put(username, userData);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put(root, new ArrayList<>(preResult.values()));

        return result;
    }
}
