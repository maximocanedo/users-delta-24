package com.max.colectiva.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
public class AccountActivity {

    private UUID id;
    private User user;
    private AccountEvent event;
    private String payload;
    private Timestamp timestamp;

    public AccountActivity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID getId() { return id; }

    @ManyToOne
    public User getUser() { return user; }

    @Enumerated(EnumType.STRING)
    public AccountEvent getEvent() { return event; }
    public String getPayload() { return payload; }
    public Timestamp getTimestamp() { return timestamp; }

    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setEvent(AccountEvent event) { this.event = event; }
    public void setPayload(String payload) { this.payload = payload; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

}
