package com.max.users.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class LoginAttempt {

    public UUID id;
    public User user;
    public boolean successful;
    public Timestamp attemptedAt;
    public String fingerprint;
    public String approximateLocation;

    public LoginAttempt() {}

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    @ManyToOne( fetch = FetchType.LAZY )
    @Column( name = "user", nullable = false )
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Column( name="success", nullable = false )
    public boolean isSuccessful() { return successful; }
    public void setSuccessful(boolean result) { this.successful = result; }

    @Column( name="attemptedAt", nullable = false )
    public Timestamp getAttemptedAt() { return attemptedAt; }
    public void setAttemptedAt(Timestamp attemptedAt) { this.attemptedAt = attemptedAt; }

    @Column( name = "fingerprint", nullable = false )
    public String getFingerprint() { return fingerprint; }
    public void setFingerprint(String fingerprint) { this.fingerprint = fingerprint; }

    @Column( name = "approximateLocation", nullable = false )
    public String getApproximateLocation() { return approximateLocation; }
    public void setApproximateLocation(String approximateLocation) { this.approximateLocation = approximateLocation; }

}
