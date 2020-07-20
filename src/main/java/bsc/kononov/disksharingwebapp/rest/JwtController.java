package bsc.kononov.disksharingwebapp.rest;

import bsc.kononov.disksharingwebapp.security.JwtManager;
import bsc.kononov.disksharingwebapp.security.JwtRequest;
import bsc.kononov.disksharingwebapp.security.JwtResponse;
import bsc.kononov.disksharingwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/get_token")
public class JwtController {

    private final JwtManager jwtManager;
    private final UserService userService;

    @Autowired
    public JwtController(JwtManager jwtManager, UserService userService) {
        this.jwtManager = jwtManager;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<JwtResponse> obtainJwt(@RequestBody JwtRequest jwtRequest) {
        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtManager.generateJwtToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
