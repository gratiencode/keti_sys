/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Objects;

/**
 *
 * @author eroot
 */
public class Credentials {
    private String username;
    private String password;

    public Credentials(String useraname, String password) {
        this.username = useraname;
        this.password = password;
    }

    public Credentials() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String useraname) {
        this.username = useraname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.username);
        hash = 89 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Credentials other = (Credentials) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Credentials{" + "useraname=" + username + ", password=" + password + '}';
    }
    
}
