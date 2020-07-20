package bsc.kononov.disksharingwebapp.security;

import java.io.Serializable;

/**
 * Учетные данные, полученные от пользователя
 */
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 9098705408675664730L;

    private String username;
    private String password;

    protected JwtRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
