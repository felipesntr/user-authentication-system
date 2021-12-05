package utils.helpers;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Encrypter {
    public boolean is_valid_password = false;
    public String value;
    public String hash;

    public boolean compare(String value, String hash) {
        boolean result = BCrypt.checkpw(value, hash);
        if (result) {
            this.is_valid_password = true;
        }
        return result;
    }
}