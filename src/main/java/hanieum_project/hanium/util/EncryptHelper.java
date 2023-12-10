package hanieum_project.hanium.util;

import org.springframework.stereotype.Component;

public interface EncryptHelper {
    String encrypt(String password);
    boolean isMatch(String password, String hashed);

}
