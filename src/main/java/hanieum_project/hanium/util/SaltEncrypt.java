package hanieum_project.hanium.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SaltEncrypt implements EncryptHelper {

    @Override
    public String encrypt(String password){return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(String password, String hashed) {return BCrypt.checkpw(password,hashed);}
}
