package bsc.kononov.disksharingwebapp.rest;

import bsc.kononov.disksharingwebapp.domain.user.User;
import bsc.kononov.disksharingwebapp.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/api/disks")
public class DiskController {

    private final DiskService diskService;

    @Autowired
    public DiskController(DiskService diskService) {
        this.diskService = diskService;
    }

    // Список собственных дисков у каждого пользователя
    @GetMapping("/own")
    public Map<String, Object> findOwnDisks() {
        return diskService.findOwnDisks();
    }

    // Список свободных дисков (у всех пользователей не взятые)
    @GetMapping("/free")
    public Map<String, Object> findFreeDisks() {
        return diskService.findFreeDisks();
    }

    // Список дисков, взятых пользователем
    @GetMapping("/taken")
    public Map<String, Object> takenByUser(HttpServletRequest request) {
        User authUser = getAuthUser(request);

        return diskService.findTakenByUsername(authUser.getUsername());
    }

    // Список дисков, взятых у пользователя (с указанием, кто взял)
    @GetMapping("/dropped")
    public Map<String, Object> droppedByUser(HttpServletRequest request) {
        User authUser = getAuthUser(request);

        return diskService.findTakenFromUsername(authUser.getUsername());
    }

    // Взять диск
    @PostMapping("/take/{diskId}")
    public String takeDisk(HttpServletRequest request, @PathVariable Long diskId) {
        User authUser = getAuthUser(request);

        return diskService.takeDisk(authUser.getUsername(), diskId);
    }

    // Отдать диск
    @PostMapping("/return/{diskId}")
    public String returnDisk(HttpServletRequest request, @PathVariable Long diskId) {
        User authUser = getAuthUser(request);

        return diskService.returnDisk(authUser.getUsername(), diskId);
    }

    private User getAuthUser(HttpServletRequest request) {
        return (User) request.getAttribute("authUser");
    }
}
