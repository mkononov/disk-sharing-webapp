package bsc.kononov.disksharingwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DiskSharingWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiskSharingWebappApplication.class, args);
    }

}
