package org.sirkostya009.security;

//  NoOp Password Encoder kinda thing
public class PasswordEncoder {
    public String encode(String password) {
        return password;
    }

    public boolean match(String encoded, String provided) {
        return encoded.equals(provided);
    }
}
