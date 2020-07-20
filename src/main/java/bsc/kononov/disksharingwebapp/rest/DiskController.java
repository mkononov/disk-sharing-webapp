package bsc.kononov.disksharingwebapp.rest;

import bsc.kononov.disksharingwebapp.security.JwtManager;
import bsc.kononov.disksharingwebapp.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@RestController
@RequestMapping("/api/disks")
public class DiskController {

    private static final String CONTACT_SUPPORT = "An error has occurred. Try to contact with support";

    private final DiskService diskService;
    private final JwtManager jwtManager;

    @Autowired
    public DiskController(DiskService diskService, JwtManager jwtManager) {
        this.diskService = diskService;
        this.jwtManager = jwtManager;
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
        String authUsername = getAuthUsername(request);

        return diskService.findTakenByUsername(authUsername);
    }

    // Список дисков, взятых у пользователя (с указанием, кто взял)
    @GetMapping("/dropped")
    public Map<String, Object> droppedByUser(HttpServletRequest request) {
        String authUsername = getAuthUsername(request);

        return diskService.findTakenFromUsername(authUsername);
    }

    // Взять диск
    @PostMapping("/take/{diskId}")
    public String takeDisk(HttpServletRequest request, @PathVariable Long diskId) {
        String authUsername = getAuthUsername(request);

        if (!authUsername.isEmpty())
            return diskService.takeDisk(authUsername, diskId);
        else
            return CONTACT_SUPPORT;
    }

    // Отдать диск
    @PostMapping("/return/{diskId}")
    public String returnDisk(HttpServletRequest request, @PathVariable Long diskId) {
        String authUsername = getAuthUsername(request);

        if (!authUsername.isEmpty())
            return diskService.returnDisk(authUsername, diskId);
        else
            return CONTACT_SUPPORT;
    }

    private String getAuthUsername(HttpServletRequest request) {
        return jwtManager.getUsernameFromHeader(request.getHeader("Authorization"));
    }
}
