/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  util.Token
 */
package util;

import java.util.Objects;

public class Token {
    private String token = "";

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.token);
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Token other = (Token)obj;
        if (!Objects.equals(this.token, other.token)) {
            return false;
        }
        return true;
    }
}

