package com.max.users.models;

import java.io.Serializable;
import java.util.Objects;

public class LinkedContactMethodId implements Serializable {

    private String owner;
    private String subject;

    public LinkedContactMethodId() {}
    public LinkedContactMethodId(String owner, String subject) {
        this.owner = owner;
        this.subject = subject;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedContactMethodId that = (LinkedContactMethodId) o;
        return owner.equals(that.owner) && subject.equals(that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, subject);
    }

}
