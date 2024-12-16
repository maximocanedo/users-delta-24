package com.max.users.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Entity
public class User implements UserDetails {

    private String username;
    private String name;
    private String surname;
    private boolean expired;
    private boolean locked;
    private boolean credentialsExpired;
    private boolean enabled;
    private List<Permission> authorities;
    private String password;

    public User() {}

    @Id
    @Column(
        name = "username",
        unique = true,
        nullable = false,
        updatable = false
    )
    @Override
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }

    @Column(
        name = "name",
        nullable = false
    )
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    @Column(
        name = "surname",
        nullable = false
    )
    public String getSurname() { return this.surname; }
    public void setSurname(String surname) { this.surname = surname; }

    @Column(
        name = "expired",
        nullable = false
    )
    public boolean isExpired() { return this.expired; }
    @Override @Transient
    public boolean isAccountNonExpired() { return !this.expired; }
    public void setExpired(boolean expired) { this.expired = expired; }

    @Column(
        name = "locked",
        nullable = false
    )
    public boolean isLocked() { return this.locked; }
    @Override @Transient
    public boolean isAccountNonLocked() { return !this.locked; }
    public void setLocked(boolean locked) { this.locked = locked; }

    @Column(
        name = "credentialsExpired",
        nullable = false
    )
    public boolean isCredentialsExpired() { return this.credentialsExpired; }
    @Override @Transient
    public boolean isCredentialsNonExpired() { return !this.credentialsExpired; }
    public void setCredentialsExpired(boolean credentialsExpired) { this.credentialsExpired = credentialsExpired; }

    @Column(
        name = "enabled",
        nullable = false
    )
    @Override @Transient
    public boolean isEnabled() { return this.enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @Override
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "username", referencedColumnName = "username")
    public List<Permission> getAuthorities() {
        return this.authorities;
    }

    @Column(
        name = "password",
        nullable = false
    )
    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

}
