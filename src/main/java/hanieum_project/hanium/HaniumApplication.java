package hanieum_project.hanium;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@SpringBootApplication
public class HaniumApplication {

	public static void main(String[] args) {
		SpringApplication.run(HaniumApplication.class, args);

	}
}


