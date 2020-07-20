package bsc.kononov.disksharingwebapp.security;

import java.io.Serializable;

/**
 * Ответ сервера на предоставленные учетные данные
 */
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8935199474246082669L;

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
