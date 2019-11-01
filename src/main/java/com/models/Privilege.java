package com.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Privilege implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String authority;

    public Privilege() {
    }

    public Privilege(String authority) {
        this.authority = authority;
    }

    public Privilege(long id, String authority) {
        this.authority = authority;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }


    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
