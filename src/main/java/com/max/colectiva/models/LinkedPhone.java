package com.max.colectiva.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@IdClass(LinkedContactMethodId.class)
public class LinkedPhone {

    private User owner;
    private String subject;
    private String hash;
    private Timestamp createdAt;
    private Timestamp verifiedAt;
    private boolean isCredential = false;

    public LinkedPhone() {}

    @Id
    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    public User getOwner() { return owner; }

    @Id
    public String getSubject() { return subject; }

    @Column(nullable = false)
    public String getHash() { return hash; }

    @Column(nullable = false)
    public Timestamp getCreatedAt() { return createdAt; }

    public Timestamp getVerifiedAt() { return verifiedAt; }

    public boolean isCredential() { return isCredential; }

    public void setOwner(User owner) { this.owner = owner; }

    public void setSubject(String subject) { this.subject = subject; }

    public void setHash(String hash) { this.hash = hash; }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public void setVerifiedAt(Timestamp verifiedAt) { this.verifiedAt = verifiedAt; }

    public void setCredential(boolean credential) { isCredential = credential; }

}
